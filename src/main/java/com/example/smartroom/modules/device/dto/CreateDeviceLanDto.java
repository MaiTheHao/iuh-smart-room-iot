package com.example.smartroom.modules.device.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDeviceLanDto {
    @NotNull(message = "Device ID is required")
    private Long deviceId;

    @NotBlank(message = "Language code is required")
    private String langCode;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;
    private String locationDescription;
}
