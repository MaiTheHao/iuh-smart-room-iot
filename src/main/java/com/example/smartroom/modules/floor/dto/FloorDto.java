package com.example.smartroom.modules.floor.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class FloorDto {
    private Long id;
    private Integer level;
    
    private String name;
    private String description;
    private String locationDescription;
    private String langCode;
    
	private Instant createdAt;
	private Instant updatedAt;
}
