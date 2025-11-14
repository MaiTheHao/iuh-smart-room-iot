package com.example.smartroom.modules.sensor.service;

import java.util.List;

import com.example.smartroom.modules.sensor.dto.CreateSensorLanDto;
import com.example.smartroom.modules.sensor.dto.SensorLanDto;
import com.example.smartroom.modules.sensor.dto.UpdateSensorLanDto;

public interface SensorLanService {
    SensorLanDto create(CreateSensorLanDto dto);
    SensorLanDto update(Long id, UpdateSensorLanDto dto);
    void delete(Long id);
    SensorLanDto getById(Long id);
    List<SensorLanDto> getBySensorId(Long sensorId);
    SensorLanDto getBySensorIdAndLangCode(Long sensorId, String langCode);
    SensorLanDto upsertBySensorIdAndLangCode(Long sensorId, String langCode, UpdateSensorLanDto dto);
    void deleteBySensorIdAndLangCode(Long sensorId, String langCode);
}
