package com.example.smartroom.device_management.dto.room;

import java.time.Instant;

public record RoomDTO(
    String id,
    String name,
    String location,
    String description,
    Instant createdAt,
    Instant updatedAt,
    Long createdBy,
    Long updatedBy,
    Integer version
) {}