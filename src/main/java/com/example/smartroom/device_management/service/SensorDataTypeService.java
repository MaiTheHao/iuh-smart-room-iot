package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.device_management.dto.sensor_data_type.SensorDataTypeCreateDTO;
import com.example.smartroom.device_management.dto.sensor_data_type.SensorDataTypeDTO;

import java.util.List;
import java.util.Optional;

public interface SensorDataTypeService {
    SensorDataTypeDTO createSensorDataType(SensorDataTypeCreateDTO dto);
    Optional<SensorDataTypeDTO> getSensorDataTypeById(String sensorId, Long dataTypeId);
    Page<SensorDataTypeDTO> getList(Pageable pageRequest);
    List<SensorDataTypeDTO> getListBySensorId(String sensorId, Pageable pageRequest);
    List<SensorDataTypeDTO> getListByDataTypeId(Long dataTypeId, Pageable pageRequest);
    boolean existsBySensorIdAndDataTypeId(String sensorId, Long dataTypeId);
    SensorDataTypeDTO deleteSensorDataTypeById(String sensorId, Long dataTypeId);
}
