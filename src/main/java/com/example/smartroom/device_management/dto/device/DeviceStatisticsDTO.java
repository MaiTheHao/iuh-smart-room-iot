package com.example.smartroom.device_management.dto.device;

public record DeviceStatisticsDTO(
    String id,
    String name,
    String location,
    String description,
    Long sensorCount
) {}