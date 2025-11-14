package com.example.smartroom.modules.sensor.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.smartroom.core.common.exception.InternalServerException;
import com.example.smartroom.core.common.exception.NotFoundException;
import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.sensor.entity.Sensor;
import com.example.smartroom.modules.sensor.entity.SensorLan;
import com.example.smartroom.modules.sensor.dto.CreateSensorDto;
import com.example.smartroom.modules.sensor.dto.SensorDto;
import com.example.smartroom.modules.sensor.dto.UpdateSensorDto;
import com.example.smartroom.modules.sensor.mapper.SensorMapper;
import com.example.smartroom.modules.sensor.repository.SensorRepository;
import com.example.smartroom.modules.sensor.service.SensorService;
import com.example.smartroom.modules.device.repository.DeviceRepository;
import com.example.smartroom.modules.sensor_type.repository.SensorTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private final SensorRepository sensorRepository;
    private final SensorMapper sensorMapper;
    private final DeviceRepository deviceRepository;
    private final SensorTypeRepository sensorTypeRepository;

    @Override
    @Transactional
    public SensorDto create(CreateSensorDto dto) {
        // Verify device exists
        deviceRepository.findById(dto.getDeviceId())
            .orElseThrow(() -> new NotFoundException("Device not found with id: " + dto.getDeviceId()));
        
        // Verify sensor type exists
        sensorTypeRepository.findById(dto.getSensorTypeId())
            .orElseThrow(() -> new NotFoundException("SensorType not found with id: " + dto.getSensorTypeId()));
        
        Sensor sensor = Sensor.builder()
            .model(dto.getModel())
            .connectionProtocol(dto.getConnectionProtocol())
            .device(deviceRepository.findById(dto.getDeviceId()).get())
            .sensorType(sensorTypeRepository.findById(dto.getSensorTypeId()).get())
            .build();
        
        sensor = sensorRepository.save(sensor);
        
        SensorDto result = sensorMapper.toDto(sensor);
        return result;
    }

    @Override
    @Transactional
    public SensorDto update(Long id, UpdateSensorDto dto, String langCode) {
        Sensor sensor = sensorRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Sensor not found with id: " + id));
        
        if (dto.getModel() != null) {
            sensor.setModel(dto.getModel());
        }
        if (dto.getConnectionProtocol() != null) {
            sensor.setConnectionProtocol(dto.getConnectionProtocol());
        }
        if (dto.getDeviceId() != null) {
            deviceRepository.findById(dto.getDeviceId())
                .ifPresentOrElse(
                    sensor::setDevice,
                    () -> {throw new NotFoundException("Device not found with id: " + dto.getDeviceId());}
                );
        }
        if (dto.getSensorTypeId() != null) {
            sensorTypeRepository.findById(dto.getSensorTypeId())
                .ifPresentOrElse(
                    sensor::setSensorType,
                    () -> {throw new NotFoundException("SensorType not found with id: " + dto.getSensorTypeId());}
                );
        }
        
        sensor = sensorRepository.save(sensor);
        SensorDto result = sensorMapper.toDto(sensor);
        
        if (langCode != null) {
            populateTranslationFields(result, sensor, langCode);
        }
        
        return result;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!sensorRepository.existsById(id)) {
            throw new NotFoundException("Sensor not found with id: " + id);
        }
        try {
            sensorRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete sensor", ex);
        }
    }

    @Override
    public SensorDto getById(Long id, String langCode) {
        Sensor sensor = sensorRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Sensor not found with id: " + id));
        
        SensorDto result = sensorMapper.toDto(sensor);
        
        if (langCode != null) {
            populateTranslationFields(result, sensor, langCode);
        }
        
        return result;
    }

    @Override
    public PaginationInfo<SensorDto> getList(Pageable pageable, String langCode) {
        Page<Sensor> sensorPage = sensorRepository.findAll(pageable);
        
        List<SensorDto> dtoList = sensorPage.getContent().stream()
            .map(sensor -> {
                SensorDto dto = sensorMapper.toDto(sensor);
                
                if (langCode != null) {
                    populateTranslationFields(dto, sensor, langCode);
                }
                
                return dto;
            })
            .toList();
        
        return PaginationInfo.from(sensorPage, dtoList);
    }

    @Override
    public PaginationInfo<SensorDto> getByDeviceId(Long deviceId, Pageable pageable, String langCode) {
        if (!deviceRepository.existsById(deviceId)) {
            throw new NotFoundException("Device not found with id: " + deviceId);
        }

        Page<Sensor> sensorPage = sensorRepository.findByDeviceId(deviceId, pageable);

        List<SensorDto> dtoList = sensorPage.getContent().stream()
            .map(sensor -> {
                SensorDto dto = sensorMapper.toDto(sensor);
                
                if (langCode != null) {
                    populateTranslationFields(dto, sensor, langCode);
                }
                
                return dto;
            })
            .toList();

        return PaginationInfo.from(sensorPage, dtoList);
    }

    @Override
    public PaginationInfo<SensorDto> getBySensorTypeId(Long sensorTypeId, Pageable pageable, String langCode) {
        if (!sensorTypeRepository.existsById(sensorTypeId)) {
            throw new NotFoundException("SensorType not found with id: " + sensorTypeId);
        }

        Page<Sensor> sensorPage = sensorRepository.findBySensorTypeId(sensorTypeId, pageable);

        List<SensorDto> dtoList = sensorPage.getContent().stream()
            .map(sensor -> {
                SensorDto dto = sensorMapper.toDto(sensor);
                
                if (langCode != null) {
                    populateTranslationFields(dto, sensor, langCode);
                }
                
                return dto;
            })
            .toList();

        return PaginationInfo.from(sensorPage, dtoList);
    }

    private void populateTranslationFields(SensorDto dto, Sensor sensor, String langCode) {
        SensorLan matchedLan = sensor.getSensorLans().stream()
            .filter(lan -> lan.getLanguage().getCode().equalsIgnoreCase(langCode))
            .findFirst()
            .orElse(null);
        
        if (matchedLan != null) {
            dto.setName(matchedLan.getName());
            dto.setDescription(matchedLan.getDescription());
            dto.setLocationDescription(matchedLan.getLocationDescription());
            dto.setLangCode(matchedLan.getLanguage().getCode());
        }
    }
}
