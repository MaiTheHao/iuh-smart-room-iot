package com.example.smartroom.modules.device.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDeviceDto {
    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "isGateway is required")
    private Boolean isGateway;

    private String model;
    private String mac;
    private String ip;
    private String connectionProtocol;

    private Long gatewayId;
}
