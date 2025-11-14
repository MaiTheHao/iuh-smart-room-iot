package com.example.smartroom.modules.sensor.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class SensorLanDto {
    private Long id;
    private String name;
    private String description;
    private String locationDescription;
    
    private String langCode;
    private Long sensorId;
    
	private Instant createdAt;
	private Instant updatedAt;
}
