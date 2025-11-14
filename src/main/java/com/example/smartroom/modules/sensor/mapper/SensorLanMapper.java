package com.example.smartroom.modules.sensor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.smartroom.modules.sensor.entity.SensorLan;
import com.example.smartroom.modules.sensor.dto.SensorLanDto;
import com.example.smartroom.modules.sensor.dto.CreateSensorLanDto;

@Mapper(componentModel = "spring")
public interface SensorLanMapper {
	SensorLanMapper INSTANCE = Mappers.getMapper(SensorLanMapper.class);

    @Mapping(source = "entity.language.code", target = "langCode")
    @Mapping(source = "entity.sensor.id", target = "sensorId")
	SensorLanDto toDto(SensorLan entity);

    @Mapping(target = "sensor", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "id", ignore = true)
	SensorLan toEntity(CreateSensorLanDto dto);
}
