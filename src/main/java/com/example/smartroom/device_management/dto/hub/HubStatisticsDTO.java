package com.example.smartroom.device_management.dto.hub;

public record HubStatisticsDTO(
    String id,
    String name,
    String location,
    String description,
    Long deviceCount,
    Long sensorCount
) {
}
