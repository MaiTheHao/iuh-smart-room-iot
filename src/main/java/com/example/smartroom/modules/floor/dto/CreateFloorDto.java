package com.example.smartroom.modules.floor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateFloorDto {
    @NotNull
    private Integer level;
}
