package com.example.smartroom.modules.sensor_type.service;

import java.util.List;

import com.example.smartroom.modules.sensor_type.dto.CreateSensorTypeLanDto;
import com.example.smartroom.modules.sensor_type.dto.SensorTypeLanDto;
import com.example.smartroom.modules.sensor_type.dto.UpdateSensorTypeLanDto;

public interface SensorTypeLanService {
    SensorTypeLanDto create(CreateSensorTypeLanDto dto);
    SensorTypeLanDto update(Long id, UpdateSensorTypeLanDto dto);
    void delete(Long id);
    SensorTypeLanDto getById(Long id);
    List<SensorTypeLanDto> getBySensorTypeId(Long sensorTypeId);
    SensorTypeLanDto getBySensorTypeIdAndLangCode(Long sensorTypeId, String langCode);
    SensorTypeLanDto upsertBySensorTypeIdAndLangCode(Long sensorTypeId, String langCode, UpdateSensorTypeLanDto dto);
    void deleteBySensorTypeIdAndLangCode(Long sensorTypeId, String langCode);
}
