package com.example.smartroom.modules.room.mapper;

import com.example.smartroom.modules.room.entity.RoomLan;
import com.example.smartroom.modules.room.dto.RoomLanDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoomLanMapper {
    RoomLanMapper INSTANCE = Mappers.getMapper(RoomLanMapper.class);

    @Mapping(source = "entity.language.code", target = "langCode")
    RoomLanDto toDto(RoomLan entity);

    @Mapping(target = "room", ignore = true)
    @Mapping(target = "language", ignore = true)
    RoomLan toEntity(RoomLanDto dto);
}
