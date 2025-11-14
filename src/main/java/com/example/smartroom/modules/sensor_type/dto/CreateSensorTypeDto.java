package com.example.smartroom.modules.sensor_type.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSensorTypeDto {
    @NotNull
    private String code;
}
