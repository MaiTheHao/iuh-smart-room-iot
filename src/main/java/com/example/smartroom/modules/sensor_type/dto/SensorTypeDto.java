package com.example.smartroom.modules.sensor_type.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class SensorTypeDto {
    private Long id;
    private String code;
    
    private String name;
    private String description;
    private String langCode;
    
	private Instant createdAt;
	private Instant updatedAt;
}
