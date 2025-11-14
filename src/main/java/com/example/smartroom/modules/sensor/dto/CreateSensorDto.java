package com.example.smartroom.modules.sensor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSensorDto {
    @NotNull
    private String model;
    
    @NotNull
    private String connectionProtocol;
    
    @NotNull
    private Long deviceId;
    
    @NotNull
    private Long sensorTypeId;
}
