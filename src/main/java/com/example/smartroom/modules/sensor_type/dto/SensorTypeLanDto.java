package com.example.smartroom.modules.sensor_type.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class SensorTypeLanDto {
    private Long id;
    private String name;
    private String description;
    
    private String langCode;
    private Long sensorTypeId;
    
	private Instant createdAt;
	private Instant updatedAt;
}
