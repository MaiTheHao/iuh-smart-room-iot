package com.example.smartroom.administration_ui.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import com.example.smartroom.device_management.dto.hub.HubDTO;
import com.example.smartroom.device_management.dto.room.RoomDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RoomDetailViewDataDTO {

    RoomDTO room;

    Page<HubDTO> hubs;

    Long totalHub;

    Long totalDevice;
    
    Long totalSensor;
}
