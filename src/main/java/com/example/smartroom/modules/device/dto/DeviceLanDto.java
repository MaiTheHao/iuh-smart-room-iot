package com.example.smartroom.modules.device.dto;

import lombok.Data;
import java.time.Instant;
import jakarta.validation.constraints.NotBlank;

@Data
public class DeviceLanDto {
    private Long id;

    @NotBlank
    private String name;

    private String description;
    private String locationDescription;

    @NotBlank
    private String langCode;

    private Instant createdAt;
    private Instant updatedAt;
}
