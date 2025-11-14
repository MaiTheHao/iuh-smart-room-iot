package com.example.smartroom.modules.sensor_type.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.smartroom.modules.sensor_type.entity.SensorTypeLan;
import com.example.smartroom.modules.sensor_type.dto.SensorTypeLanDto;
import com.example.smartroom.modules.sensor_type.dto.CreateSensorTypeLanDto;

@Mapper(componentModel = "spring")
public interface SensorTypeLanMapper {
	SensorTypeLanMapper INSTANCE = Mappers.getMapper(SensorTypeLanMapper.class);

    @Mapping(source = "entity.language.code", target = "langCode")
    @Mapping(source = "entity.sensorType.id", target = "sensorTypeId")
	SensorTypeLanDto toDto(SensorTypeLan entity);

    @Mapping(target = "sensorType", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "id", ignore = true)
	SensorTypeLan toEntity(CreateSensorTypeLanDto dto);
}
