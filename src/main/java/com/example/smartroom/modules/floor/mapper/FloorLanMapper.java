package com.example.smartroom.modules.floor.mapper;

import com.example.smartroom.modules.floor.entity.FloorLan;
import com.example.smartroom.modules.floor.dto.FloorLanDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FloorLanMapper {
	FloorLanMapper INSTANCE = Mappers.getMapper(FloorLanMapper.class);

    @Mapping(source = "entity.language.code", target = "langCode")
	FloorLanDto toDto(FloorLan entity);

    @Mapping(target = "floor", ignore = true)
    @Mapping(target = "language", ignore = true)
	FloorLan toEntity(FloorLanDto dto);
}
