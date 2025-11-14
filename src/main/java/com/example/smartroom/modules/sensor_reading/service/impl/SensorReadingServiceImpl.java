package com.example.smartroom.modules.sensor_reading.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smartroom.core.common.exception.InternalServerException;
import com.example.smartroom.core.common.exception.NotFoundException;
import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.sensor.entity.Sensor;
import com.example.smartroom.modules.sensor.repository.SensorRepository;
import com.example.smartroom.modules.sensor_reading.dto.CreateBatchSensorReadingDto;
import com.example.smartroom.modules.sensor_reading.dto.CreateSensorReadingDto;
import com.example.smartroom.modules.sensor_reading.dto.SensorReadingDto;
import com.example.smartroom.modules.sensor_reading.dto.SensorReadingStatisticsDto;
import com.example.smartroom.modules.sensor_reading.entity.SensorReading;
import com.example.smartroom.modules.sensor_reading.mapper.SensorReadingMapper;
import com.example.smartroom.modules.sensor_reading.repository.SensorReadingRepository;
import com.example.smartroom.modules.sensor_reading.service.SensorReadingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SensorReadingServiceImpl implements SensorReadingService {
    private final SensorReadingRepository sensorReadingRepository;
    private final SensorRepository sensorRepository;
    private final SensorReadingMapper sensorReadingMapper;

    @Override
    @Transactional
    public SensorReadingDto create(CreateSensorReadingDto dto) {
        // Verify sensor exists
        Sensor sensor = sensorRepository.findById(dto.getSensorId())
            .orElseThrow(() -> new NotFoundException("Sensor not found with id: " + dto.getSensorId()));
        
        SensorReading reading = sensorReadingMapper.toEntity(dto);
        reading.setSensor(sensor);
        
        reading = sensorReadingRepository.save(reading);
        
        return sensorReadingMapper.toDto(reading);
    }

    @Override
    @Transactional
    public List<SensorReadingDto> createBatch(CreateBatchSensorReadingDto dto) {
        List<SensorReading> readings = dto.getReadings().stream()
            .map(createDto -> {
                // Verify sensor exists
                Sensor sensor = sensorRepository.findById(createDto.getSensorId())
                    .orElseThrow(() -> new NotFoundException("Sensor not found with id: " + createDto.getSensorId()));
                
                SensorReading reading = sensorReadingMapper.toEntity(createDto);
                reading.setSensor(sensor);
                return reading;
            })
            .collect(Collectors.toList());
        
        List<SensorReading> savedReadings = sensorReadingRepository.saveAll(readings);
        
        return savedReadings.stream()
            .map(sensorReadingMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SensorReadingDto createWithAutoTimestamp(
            com.example.smartroom.modules.sensor_reading.dto.CreateSensorReadingWithoutTimestampDto dto) {
        // Verify sensor exists
        Sensor sensor = sensorRepository.findById(dto.getSensorId())
            .orElseThrow(() -> new NotFoundException("Sensor not found with id: " + dto.getSensorId()));
        
        // Build reading with server timestamp
        SensorReading reading = SensorReading.builder()
            .sensor(sensor)
            .tempC(dto.getTempC())
            .volt(dto.getVolt())
            .ampe(dto.getAmpe())
            .watt(dto.getWatt())
            .wattHour(dto.getWattHour())
            .hz(dto.getHz())
            .powerFactor(dto.getPowerFactor())
            .timestamp(Instant.now())
            .build();
        
        reading = sensorReadingRepository.save(reading);
        return sensorReadingMapper.toDto(reading);
    }

    @Override
    public SensorReadingDto getById(Long id) {
        SensorReading reading = sensorReadingRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("SensorReading not found with id: " + id));
        
        return sensorReadingMapper.toDto(reading);
    }

    @Override
    public PaginationInfo<SensorReadingDto> getList(Pageable pageable) {
        Page<SensorReading> readingPage = sensorReadingRepository.findAll(pageable);
        
        List<SensorReadingDto> dtoList = readingPage.getContent().stream()
            .map(sensorReadingMapper::toDto)
            .toList();
        
        return PaginationInfo.from(readingPage, dtoList);
    }

    @Override
    public PaginationInfo<SensorReadingDto> getBySensorId(
            Long sensorId, 
            Instant startTime, 
            Instant endTime, 
            Pageable pageable) {
        // Verify sensor exists
        if (!sensorRepository.existsById(sensorId)) {
            throw new NotFoundException("Sensor not found with id: " + sensorId);
        }
        
        Page<SensorReading> readingPage;
        
        // If time range is specified, use filtered query
        if (startTime != null && endTime != null) {
            readingPage = sensorReadingRepository.findBySensorIdAndTimestampBetween(
                sensorId, startTime, endTime, pageable);
        } else {
            readingPage = sensorReadingRepository.findBySensorId(sensorId, pageable);
        }
        
        List<SensorReadingDto> dtoList = readingPage.getContent().stream()
            .map(sensorReadingMapper::toDto)
            .toList();
        
        return PaginationInfo.from(readingPage, dtoList);
    }

    @Override
    public List<SensorReadingDto> getLatestBySensorId(Long sensorId, int limit) {
        // Verify sensor exists
        if (!sensorRepository.existsById(sensorId)) {
            throw new NotFoundException("Sensor not found with id: " + sensorId);
        }
        
        Pageable pageable = PageRequest.of(0, limit);
        List<SensorReading> readings = sensorReadingRepository.findLatestBySensorId(sensorId, pageable);
        
        return readings.stream()
            .map(sensorReadingMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public PaginationInfo<SensorReadingDto> getByMultipleSensors(
            List<Long> sensorIds,
            Instant startTime,
            Instant endTime,
            Pageable pageable) {
        
        Page<SensorReading> readingPage = sensorReadingRepository.findBySensorIdsAndTimestampBetween(
            sensorIds, startTime, endTime, pageable);
        
        List<SensorReadingDto> dtoList = readingPage.getContent().stream()
            .map(sensorReadingMapper::toDto)
            .toList();
        
        return PaginationInfo.from(readingPage, dtoList);
    }

    @Override
    public SensorReadingStatisticsDto getStatistics(Long sensorId, Instant startTime, Instant endTime) {
        // Verify sensor exists
        if (!sensorRepository.existsById(sensorId)) {
            throw new NotFoundException("Sensor not found with id: " + sensorId);
        }
        
        Object[] stats = sensorReadingRepository.getStatisticsBySensorIdAndTimestampBetween(
            sensorId, startTime, endTime);
        
        // If no data, return empty statistics
        if (stats == null || stats[0] == null) {
            return SensorReadingStatisticsDto.builder()
                .sensorId(sensorId)
                .count(0L)
                .build();
        }
        
        return SensorReadingStatisticsDto.builder()
            .sensorId(sensorId)
            .count(stats[0] != null ? ((Number) stats[0]).longValue() : 0L)
            .avgTempC(stats[1] != null ? ((Number) stats[1]).doubleValue() : null)
            .minTempC(stats[2] != null ? ((Number) stats[2]).doubleValue() : null)
            .maxTempC(stats[3] != null ? ((Number) stats[3]).doubleValue() : null)
            .avgVolt(stats[4] != null ? ((Number) stats[4]).doubleValue() : null)
            .minVolt(stats[5] != null ? ((Number) stats[5]).doubleValue() : null)
            .maxVolt(stats[6] != null ? ((Number) stats[6]).doubleValue() : null)
            .avgAmpe(stats[7] != null ? ((Number) stats[7]).doubleValue() : null)
            .minAmpe(stats[8] != null ? ((Number) stats[8]).doubleValue() : null)
            .maxAmpe(stats[9] != null ? ((Number) stats[9]).doubleValue() : null)
            .avgWatt(stats[10] != null ? ((Number) stats[10]).doubleValue() : null)
            .minWatt(stats[11] != null ? ((Number) stats[11]).doubleValue() : null)
            .maxWatt(stats[12] != null ? ((Number) stats[12]).doubleValue() : null)
            .totalWattHour(stats[13] != null ? ((Number) stats[13]).doubleValue() : null)
            .avgWattHour(stats[14] != null ? ((Number) stats[14]).doubleValue() : null)
            .avgHz(stats[15] != null ? ((Number) stats[15]).doubleValue() : null)
            .minHz(stats[16] != null ? ((Number) stats[16]).doubleValue() : null)
            .maxHz(stats[17] != null ? ((Number) stats[17]).doubleValue() : null)
            .avgPowerFactor(stats[18] != null ? ((Number) stats[18]).doubleValue() : null)
            .minPowerFactor(stats[19] != null ? ((Number) stats[19]).doubleValue() : null)
            .maxPowerFactor(stats[20] != null ? ((Number) stats[20]).doubleValue() : null)
            .build();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!sensorReadingRepository.existsById(id)) {
            throw new NotFoundException("SensorReading not found with id: " + id);
        }
        try {
            sensorReadingRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete sensor reading", ex);
        }
    }

    @Override
    @Transactional
    public int deleteBySensorId(Long sensorId, Instant startTime, Instant endTime) {
        // Verify sensor exists
        if (!sensorRepository.existsById(sensorId)) {
            throw new NotFoundException("Sensor not found with id: " + sensorId);
        }
        
        try {
            // If time range specified, delete with filter
            if (startTime != null && endTime != null) {
                return sensorReadingRepository.deleteBySensorIdAndTimestampBetween(
                    sensorId, startTime, endTime);
            } else {
                // Delete all readings for sensor (be careful!)
                return sensorReadingRepository.deleteBySensorIdAndTimestampBetween(
                    sensorId, Instant.MIN, Instant.MAX);
            }
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete sensor readings", ex);
        }
    }
}
