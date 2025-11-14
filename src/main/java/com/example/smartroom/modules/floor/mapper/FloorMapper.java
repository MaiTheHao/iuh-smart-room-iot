package com.example.smartroom.modules.floor.mapper;

import com.example.smartroom.modules.floor.dto.CreateFloorDto;
import com.example.smartroom.modules.floor.dto.FloorDto;
import com.example.smartroom.modules.floor.dto.UpdateFloorDto;
import com.example.smartroom.modules.floor.entity.Floor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FloorMapper {
	FloorMapper INSTANCE = Mappers.getMapper(FloorMapper.class);

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "description", ignore = true)
	@Mapping(target = "locationDescription", ignore = true)
	@Mapping(target = "langCode", ignore = true)
	FloorDto toDto(Floor entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "rooms", ignore = true)
	@Mapping(target = "floorLans", ignore = true)
	Floor toEntity(CreateFloorDto dto);

	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "floorLans", ignore = true)
	@Mapping(target = "rooms", ignore = true)
	@Mapping(target = "id", ignore = true)	
	void updateEntityFromDto(@MappingTarget Floor entity, UpdateFloorDto dto);
}
