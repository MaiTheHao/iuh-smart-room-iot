package com.example.smartroom.modules.room.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRoomDto {
    @NotNull(message = "Floor ID is required")
    private Long floorId;
}
