package com.example.smartroom.device_management.dto.room;

import java.time.Instant;

public record RoomStatisticsDTO(
    String id,
    String name,
    String location,
    String description,
    Long hubCount,
    Long deviceCount,
    Long sensorCount,
    Instant createdAt,
    Instant updatedAt,
    Long createdBy,
    Long updatedBy,
    Integer version
) {}