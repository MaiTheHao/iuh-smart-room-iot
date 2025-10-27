package com.example.smartroom.device_management.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.device_management.dto.sensor_data_type.SensorDataTypeCreateDTO;
import com.example.smartroom.device_management.dto.sensor_data_type.SensorDataTypeDTO;
import com.example.smartroom.device_management.entity.SensorDataType;
import com.example.smartroom.device_management.mapper.SensorDataTypeMapper;
import com.example.smartroom.device_management.repository.DataTypeRepository;
import com.example.smartroom.device_management.repository.SensorDataTypeRepository;
import com.example.smartroom.device_management.repository.SensorRepository;
import com.example.smartroom.device_management.service.SensorDataTypeService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SensorDataTypeServiceImpl implements SensorDataTypeService {

    private final SensorDataTypeRepository sensorDataTypeRepository;
    private final SensorRepository sensorRepository;
    private final DataTypeRepository dataTypeRepository;
    private final SensorDataTypeMapper sensorDataTypeMapper;

    @Override
    @Transactional
    public SensorDataTypeDTO createSensorDataType(SensorDataTypeCreateDTO dto) {
        if (!sensorRepository.existsById(dto.getSensorId())) {
            throw new NotFoundException("Sensor with id " + dto.getSensorId() + " not found");
        }

        if (!dataTypeRepository.existsById(dto.getDataTypeId())) {
            throw new NotFoundException("Data type with id " + dto.getDataTypeId() + " not found");
        }

        if (existsBySensorIdAndDataTypeId(dto.getSensorId(), dto.getDataTypeId())) {
            throw new BadRequestException("Sensor " + dto.getSensorId() + " already has data type " + dto.getDataTypeId());
        }

        SensorDataType sensorDataType = sensorDataTypeMapper.toEntity(dto);
        return sensorDataTypeMapper.toDTO(sensorDataTypeRepository.save(sensorDataType));
    }

    @Override
    public Optional<SensorDataTypeDTO> getSensorDataTypeById(String sensorId, Long dataTypeId) {
        return sensorDataTypeRepository.findBySensorIdAndDataTypeId(sensorId, dataTypeId)
                .map(sensorDataTypeMapper::toDTO);
    }

    @Override
    public Page<SensorDataTypeDTO> getList(Pageable pageRequest) {
        return sensorDataTypeRepository.findAll(pageRequest)
                .map(sensorDataTypeMapper::toDTO);
    }

    @Override
    public List<SensorDataTypeDTO> getListBySensorId(String sensorId, Pageable pageRequest) {
        return sensorDataTypeRepository.findBySensorId(sensorId, pageRequest)
                .stream()
                .map(sensorDataTypeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SensorDataTypeDTO> getListByDataTypeId(Long dataTypeId, Pageable pageRequest) {
        return sensorDataTypeRepository.findByDataTypeId(dataTypeId, pageRequest)
                .stream()
                .map(sensorDataTypeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsBySensorIdAndDataTypeId(String sensorId, Long dataTypeId) {
        return sensorDataTypeRepository.existsBySensorIdAndDataTypeId(sensorId, dataTypeId);
    }

    @Override
    @Transactional
    public SensorDataTypeDTO deleteSensorDataTypeById(String sensorId, Long dataTypeId) {
        SensorDataType sensorDataType = sensorDataTypeRepository.findBySensorIdAndDataTypeId(sensorId, dataTypeId)
                .orElseThrow(() -> new RuntimeException("SensorDataType with sensorId " + sensorId + " and dataTypeId " + dataTypeId + " not found"));
        sensorDataTypeRepository.delete(sensorDataType);
        return sensorDataTypeMapper.toDTO(sensorDataType);
    }
}
