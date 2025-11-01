package com.example.smartroom.device_management.dto.device;

import java.time.Instant;

import com.example.smartroom.common.enumeration.ComponentStatus;

public record DeviceDTO(
    String id,
    String name,
    String location,
    ComponentStatus status,
    String description,
    String hubId,
    Instant createdAt,
    Instant updatedAt,
    Long createdBy,
    Long updatedBy,
    Integer version,
    String connectionTypeId
){}