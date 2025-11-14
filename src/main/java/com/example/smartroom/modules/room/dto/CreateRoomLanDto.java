package com.example.smartroom.modules.room.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRoomLanDto {
    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotBlank(message = "Language code is required")
    private String langCode;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;
    private String locationDescription;
}
