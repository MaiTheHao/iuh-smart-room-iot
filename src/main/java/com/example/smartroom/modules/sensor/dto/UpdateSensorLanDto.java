package com.example.smartroom.modules.sensor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSensorLanDto {
    @NotBlank
    private String name;
    
    private String description;
    
    private String locationDescription;
}
