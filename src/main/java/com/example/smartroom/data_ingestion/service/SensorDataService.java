package com.example.smartroom.data_ingestion.service;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.data_ingestion.dto.sensor_data.SensorDataCreateDTO;
import com.example.smartroom.data_ingestion.dto.sensor_data.SensorDataDTO;

public interface SensorDataService {
    SensorDataDTO create(SensorDataCreateDTO dto);

    SensorDataDTO getById(Long id);

    Page<SensorDataDTO> getList(Pageable pageRequest);

    Page<SensorDataDTO> getListBySensorId(Long sensorId, Pageable pageRequest);

    Page<SensorDataDTO> getListByDataTypeId(Long dataTypeId, Pageable pageRequest);
    
    Page<SensorDataDTO> getListBySensorIdAndDataTypeId(Long sensorId, Long dataTypeId, Pageable pageRequest);
    
    Page<SensorDataDTO> getListBySensorIdAndRecordedAtBetween(Long sensorId, Instant startTime, Instant endTime, Pageable pageRequest);
    
    void delete(Long id);
}
