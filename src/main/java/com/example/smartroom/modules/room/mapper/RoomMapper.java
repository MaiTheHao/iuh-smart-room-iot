package com.example.smartroom.modules.room.mapper;

import com.example.smartroom.modules.room.dto.CreateRoomDto;
import com.example.smartroom.modules.room.dto.RoomDto;
import com.example.smartroom.modules.room.dto.UpdateRoomDto;
import com.example.smartroom.modules.room.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);
    
    @Mapping(target = "floorId", source = "floor.id")
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "locationDescription", ignore = true)
    @Mapping(target = "langCode", ignore = true)
    RoomDto toDto(Room entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "floor", ignore = true)
    @Mapping(target = "devices", ignore = true)
    @Mapping(target = "roomLans", ignore = true)
    Room toEntity(CreateRoomDto dto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "floor", ignore = true)
    @Mapping(target = "roomLans", ignore = true)
    @Mapping(target = "devices", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(@MappingTarget Room entity, UpdateRoomDto dto);
}
