package com.example.smartroom.device_management.dto.sensor;

import java.time.Instant;

import com.example.smartroom.common.enumeration.ComponentStatus;

public record SensorDTO(

    String id,

    String name,

    String location,

    ComponentStatus status,

    String description,

    String deviceId,

    Instant createdAt,

    Instant updatedAt,

    String createdBy,

    String updatedBy,

    Integer version
) {
}
