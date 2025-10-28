package com.example.smartroom.device_management.dto.sensor;

import com.example.smartroom.common.enumeration.ComponentStatus;

public record SensorStatisticsDTO(
    String id,
    String name,
    String location,
    String description,
    ComponentStatus status,
    String deviceId,
    Long dataTypeCount
) {}
