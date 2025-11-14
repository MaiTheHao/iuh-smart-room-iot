package com.example.smartroom.modules.sensor_reading.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorReadingStatisticsDto {
    private Long sensorId;
    private Long count;
    
    // Temperature statistics
    private Double avgTempC;
    private Double minTempC;
    private Double maxTempC;
    
    // Voltage statistics
    private Double avgVolt;
    private Double minVolt;
    private Double maxVolt;
    
    // Current statistics
    private Double avgAmpe;
    private Double minAmpe;
    private Double maxAmpe;
    
    // Power statistics
    private Double avgWatt;
    private Double minWatt;
    private Double maxWatt;
    
    // Energy statistics
    private Double totalWattHour;
    private Double avgWattHour;
    
    // Frequency statistics
    private Double avgHz;
    private Double minHz;
    private Double maxHz;
    
    // Power factor statistics
    private Double avgPowerFactor;
    private Double minPowerFactor;
    private Double maxPowerFactor;
}
