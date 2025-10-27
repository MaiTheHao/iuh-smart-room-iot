package com.example.smartroom.administration_ui.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import com.example.smartroom.administration_ui.dto.RoomDetailViewDataDTO;
import com.example.smartroom.device_management.dto.room.RoomDTO;
import com.example.smartroom.device_management.dto.hub.HubDTO;

@Mapper(componentModel = "spring")
public interface RoomDetailViewDataMapper {
    @Mapping(target = "room", source = "room")
    @Mapping(target = "hubs", source = "hubs")
    @Mapping(target = "totalHub", source = "totalHub")
    @Mapping(target = "totalDevice", source = "totalDevice")
    @Mapping(target = "totalSensor", source = "totalSensor")
    RoomDetailViewDataDTO toDTO(
        RoomDTO room,
        Page<HubDTO> hubs,
        Long totalHub,
        Long totalDevice,
        Long totalSensor
    );
}
