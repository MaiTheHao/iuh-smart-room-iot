package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.device_management.dto.sensor.SensorCreateDTO;
import com.example.smartroom.device_management.dto.sensor.SensorDTO;

public interface SensorService {
    SensorDTO createSensor(SensorCreateDTO dto);
    SensorDTO getSensorById(String id);
    Page<SensorDTO> getList(Pageable pageRequest);
    Page<SensorDTO> getListByDeviceId(String deviceId, Pageable pageRequest);
    SensorDTO deleteSensorById(String id);
    Long count();
    Long countByDeviceId(String deviceId);
    Long countByHubId(String hubId);
    Long countByRoomId(String roomId);
}
