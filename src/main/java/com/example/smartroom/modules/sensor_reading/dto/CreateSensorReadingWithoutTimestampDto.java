package com.example.smartroom.modules.sensor_reading.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for creating sensor reading with server-generated timestamp
 * Used when IoT device doesn't have reliable clock
 */
@Data
public class CreateSensorReadingWithoutTimestampDto {
    @NotNull(message = "Sensor ID is required")
    private Long sensorId;
    
    private Double tempC;
    private Double volt;
    private Double ampe;
    private Double watt;
    private Double wattHour;
    private Double hz;
    private Double powerFactor;
}
