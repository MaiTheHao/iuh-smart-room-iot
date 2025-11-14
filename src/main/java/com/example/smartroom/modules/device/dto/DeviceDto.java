package com.example.smartroom.modules.device.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class DeviceDto {
    private Long id;
    private Boolean isGateway;
    private String model;
    private String mac;
    private String ip;
    private String connectionProtocol;
    private Long roomId;
    private Long gatewayId;

    // Translation fields
    private String name;
    private String description;
    private String locationDescription;
    private String langCode;

    private Instant createdAt;
    private Instant updatedAt;
}
