package com.example.smartroom.modules.device.dto;

import lombok.Data;

@Data
public class UpdateDeviceDto {
    private Boolean isGateway;
    private String model;
    private String mac;
    private String ip;
    private String connectionProtocol;
    private Long roomId;
    private Long gatewayId;
}
