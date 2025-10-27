package com.example.smartroom.administration_ui.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.example.smartroom.administration_ui.dto.RoomListViewDataDTO;
import com.example.smartroom.device_management.dto.room.RoomDTO;

@Mapper(componentModel = "spring")
public interface RoomListViewDataMapper {
    @Mapping(target = "rooms", source = "rooms")
    RoomListViewDataDTO toDTO(Page<RoomDTO> rooms);
}
