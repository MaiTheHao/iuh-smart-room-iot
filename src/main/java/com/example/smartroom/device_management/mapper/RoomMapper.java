package com.example.smartroom.device_management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.smartroom.device_management.dto.room.RoomCreateDTO;
import com.example.smartroom.device_management.dto.room.RoomDTO;
import com.example.smartroom.device_management.entity.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomDTO toDTO(Room entity);

    @Mapping(target = "hubs", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    Room toEntity(RoomCreateDTO dto);
}
