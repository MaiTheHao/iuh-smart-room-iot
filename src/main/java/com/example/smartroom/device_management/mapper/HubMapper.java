package com.example.smartroom.device_management.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import com.example.smartroom.device_management.dto.hub.HubCreateDTO;
import com.example.smartroom.device_management.dto.hub.HubDTO;
import com.example.smartroom.device_management.entity.Hub;
import com.example.smartroom.device_management.entity.Room;

@Mapper(componentModel = "spring")
public interface HubMapper {

    HubMapper INSTANCE = Mappers.getMapper(HubMapper.class);

    @Mapping(source = "room.id", target = "roomId")
    HubDTO toDTO(Hub hub);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "room", source = "room")
    @Mapping(target = "location", source = "dto.location")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "devices", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    Hub toEntity(HubCreateDTO dto, Room room);
}
