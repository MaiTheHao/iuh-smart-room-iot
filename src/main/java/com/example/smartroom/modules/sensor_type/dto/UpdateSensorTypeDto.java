package com.example.smartroom.modules.sensor_type.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSensorTypeDto {
    @NotNull
    private String code;
}
