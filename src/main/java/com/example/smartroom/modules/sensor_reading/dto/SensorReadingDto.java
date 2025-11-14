package com.example.smartroom.modules.sensor_reading.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class SensorReadingDto {
    private Long id;
    private Long sensorId;
    private Double tempC;
    private Double volt;
    private Double ampe;
    private Double watt;
    private Double wattHour;
    private Double hz;
    private Double powerFactor;
    private Instant timestamp;
}
