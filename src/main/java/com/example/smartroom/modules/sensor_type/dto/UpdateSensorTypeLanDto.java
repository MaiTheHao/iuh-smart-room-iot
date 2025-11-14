package com.example.smartroom.modules.sensor_type.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSensorTypeLanDto {
    @NotBlank
    private String name;
    
    private String description;
}
