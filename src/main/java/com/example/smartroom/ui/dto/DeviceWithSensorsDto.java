package com.example.smartroom.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceWithSensorsDto {
    private Long id;
    private String name;
    private String model;
    private String mac;
    private String ip;
    private Boolean isGateway;
    private String connectionProtocol;
    
    private Long roomId;
    private String roomName;
    private String floorLevel;
    
    private List<SensorInfoDto> sensors;
    
    private Double avgWattHour;
    private String status;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SensorInfoDto {
        private Long id;
        private String name;
        private String model;
        private String type;
        private Long sensorTypeId;
    }
}
