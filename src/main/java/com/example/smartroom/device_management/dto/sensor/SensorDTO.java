package com.example.smartroom.device_management.dto.sensor;

import java.time.Instant;
import java.util.Set;

import com.example.smartroom.common.enumeration.ComponentStatus;
import com.example.smartroom.device_management.dto.sensor_data_type.SensorDataTypeDTO;

public record SensorDTO(
    String id,
    String name,
    String location,
    ComponentStatus status,
    String description,
    Set<SensorDataTypeDTO> sensorDataTypes,
    String deviceId,
    Instant createdAt,
    Instant updatedAt,
    String createdBy,
    String updatedBy,
    Integer version
) {
}
