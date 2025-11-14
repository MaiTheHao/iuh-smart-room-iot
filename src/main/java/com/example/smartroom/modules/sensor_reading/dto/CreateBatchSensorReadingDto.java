package com.example.smartroom.modules.sensor_reading.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class CreateBatchSensorReadingDto {
    @NotEmpty(message = "Readings list cannot be empty")
    @Valid
    private List<CreateSensorReadingDto> readings;
}
