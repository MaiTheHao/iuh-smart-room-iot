package com.example.smartroom.modules.sensor.dto;

import lombok.Data;

@Data
public class UpdateSensorDto {
    private String model;
    
    private String connectionProtocol;
    
    private Long deviceId;
    
    private Long sensorTypeId;
}
