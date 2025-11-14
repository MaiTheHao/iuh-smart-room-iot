package com.example.smartroom.modules.floor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateFloorLanDto {
    @NotNull(message = "Floor ID is required")
    private Long floorId;

    @NotBlank(message = "Language code is required")
    private String langCode;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;
    private String locationDescription;
}
