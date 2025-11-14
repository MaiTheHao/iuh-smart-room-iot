package com.example.smartroom.modules.sensor_type.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.smartroom.modules.sensor_type.entity.SensorType;
import com.example.smartroom.modules.sensor_type.dto.SensorTypeDto;
import com.example.smartroom.modules.sensor_type.dto.CreateSensorTypeDto;

@Mapper(componentModel = "spring")
public interface SensorTypeMapper {
	SensorTypeMapper INSTANCE = Mappers.getMapper(SensorTypeMapper.class);

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "description", ignore = true)
	@Mapping(target = "langCode", ignore = true)
	SensorTypeDto toDto(SensorType entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "sensors", ignore = true)
	@Mapping(target = "sensorTypeLans", ignore = true)
	SensorType toEntity(CreateSensorTypeDto dto);
}
