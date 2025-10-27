package com.example.smartroom.data_ingestion.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.data_ingestion.dto.sensor_data.SensorDataCreateDTO;
import com.example.smartroom.data_ingestion.dto.sensor_data.SensorDataDTO;
import com.example.smartroom.data_ingestion.entity.SensorData;
import com.example.smartroom.data_ingestion.mapper.SensorDataMapper;
import com.example.smartroom.data_ingestion.repository.SensorDataRepository;
import com.example.smartroom.data_ingestion.service.SensorDataService;
import com.example.smartroom.device_management.entity.DataType;
import com.example.smartroom.device_management.entity.Sensor;
import com.example.smartroom.device_management.repository.DataTypeRepository;
import com.example.smartroom.device_management.repository.SensorRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.Instant;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SensorDataServiceImpl implements SensorDataService {

    private final SensorDataRepository sensorDataRepository;
    private final SensorRepository sensorRepository;
    private final DataTypeRepository dataTypeRepository;
    private final SensorDataMapper sensorDataMapper;

    @Override
    @Transactional
    public SensorDataDTO create(SensorDataCreateDTO dto) {
        Sensor sensor = sensorRepository.findById(dto.getSensorId())
                .orElseThrow(() -> new NotFoundException("Sensor data sensor not found"));
        DataType dataType = dataTypeRepository.findByCode(dto.getDataTypeCode())
                .orElseThrow(() -> new NotFoundException("Sensor data data type not found"));

        if (dto.getValue() == null) {
            throw new BadRequestException("Sensor data value must not be null");
        }

        SensorData sensorData = sensorDataMapper.toEntity(dto);
        sensorData.setSensor(sensor);
        sensorData.setDataType(dataType);

        SensorData saved = sensorDataRepository.save(sensorData);
        return sensorDataMapper.toDTO(saved);
    }

    @Override
    public SensorDataDTO getById(Long id) {
        SensorData sensorData = sensorDataRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("SensorData with id " + id + " not found"));
        return sensorDataMapper.toDTO(sensorData);
    }

    @Override
    public Page<SensorDataDTO> getList(Pageable pageRequest) {
        Page<SensorData> page = sensorDataRepository.findAll(pageRequest);
        return page.map(sensorDataMapper::toDTO);
    }

    @Override
    public Page<SensorDataDTO> getListBySensorId(Long sensorId, Pageable pageRequest) {
        Page<SensorData> page = sensorDataRepository.findBySensorId(sensorId, pageRequest);
        return page.map(sensorDataMapper::toDTO);
    }

    @Override
    public Page<SensorDataDTO> getListByDataTypeId(Long dataTypeId, Pageable pageRequest) {
        Page<SensorData> page = sensorDataRepository.findByDataTypeId(dataTypeId, pageRequest);
        return page.map(sensorDataMapper::toDTO);
    }

    @Override
    public Page<SensorDataDTO> getListBySensorIdAndDataTypeId(Long sensorId, Long dataTypeId, Pageable pageRequest) {
        Page<SensorData> page = sensorDataRepository.findBySensorIdAndDataTypeId(sensorId, dataTypeId, pageRequest);
        return page.map(sensorDataMapper::toDTO);
    }

    @Override
    public Page<SensorDataDTO> getListBySensorIdAndRecordedAtBetween(Long sensorId, Instant startTime, Instant endTime, Pageable pageRequest) {
        Page<SensorData> page = sensorDataRepository.findBySensorIdAndRecordedAtBetween(sensorId, startTime, endTime, pageRequest);
        return page.map(sensorDataMapper::toDTO);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!sensorDataRepository.existsById(id)) {
            throw new NotFoundException("Sensor data with id " + id + " not found");
        }
        sensorDataRepository.deleteById(id);
    }
}
