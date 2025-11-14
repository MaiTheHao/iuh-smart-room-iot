package com.example.smartroom.modules.sensor.service;

import org.springframework.data.domain.Pageable;

import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.sensor.dto.CreateSensorDto;
import com.example.smartroom.modules.sensor.dto.SensorDto;
import com.example.smartroom.modules.sensor.dto.UpdateSensorDto;

public interface SensorService {
    SensorDto create(CreateSensorDto dto);
    SensorDto update(Long id, UpdateSensorDto dto, String langCode);
    void delete(Long id);
    SensorDto getById(Long id, String langCode);
    PaginationInfo<SensorDto> getList(Pageable pageable, String langCode);
    PaginationInfo<SensorDto> getByDeviceId(Long deviceId, Pageable pageable, String langCode);
    PaginationInfo<SensorDto> getBySensorTypeId(Long sensorTypeId, Pageable pageable, String langCode);
}
