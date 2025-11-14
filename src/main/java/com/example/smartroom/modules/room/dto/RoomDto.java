package com.example.smartroom.modules.room.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private Long floorId;
    
    private String name;
    private String description;
    private String locationDescription;
    private String langCode;
    
    private Instant createdAt;
    private Instant updatedAt;
}
