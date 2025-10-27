package com.example.smartroom.administration_ui.dto;

import org.springframework.data.domain.Page;

import com.example.smartroom.device_management.dto.room.RoomDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PACKAGE)
public class RoomListViewDataDTO {
    
    Page<RoomDTO> rooms;
}
