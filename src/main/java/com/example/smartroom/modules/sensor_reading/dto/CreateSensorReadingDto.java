package com.example.smartroom.modules.sensor_reading.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.Instant;

@Data
public class CreateSensorReadingDto {
    @NotNull(message = "Sensor ID is required")
    private Long sensorId;
    
    private Double tempC;
    private Double volt;
    private Double ampe;
    private Double watt;
    private Double wattHour;
    private Double hz;
    private Double powerFactor;
    
    @NotNull(message = "Timestamp is required")
    private Instant timestamp;
}
