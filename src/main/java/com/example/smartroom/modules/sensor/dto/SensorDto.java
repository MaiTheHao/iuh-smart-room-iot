package com.example.smartroom.modules.sensor.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class SensorDto {
    private Long id;
    private String model;
    private String connectionProtocol;
    
    private String name;
    private String description;
    private String locationDescription;
    private String langCode;
    
    private Long deviceId;
    private Long sensorTypeId;
    
	private Instant createdAt;
	private Instant updatedAt;
}
