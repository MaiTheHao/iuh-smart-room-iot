package com.example.smartroom.modules.sensor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.example.smartroom.modules.sensor.entity.Sensor;
import com.example.smartroom.modules.sensor.dto.SensorDto;
import com.example.smartroom.modules.sensor.dto.CreateSensorDto;
import com.example.smartroom.modules.sensor.dto.UpdateSensorDto;

@Mapper(componentModel = "spring")
public interface SensorMapper {
	SensorMapper INSTANCE = Mappers.getMapper(SensorMapper.class);

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "description", ignore = true)
	@Mapping(target = "locationDescription", ignore = true)
	@Mapping(target = "langCode", ignore = true)
	@Mapping(source = "entity.device.id", target = "deviceId")
	@Mapping(source = "entity.sensorType.id", target = "sensorTypeId")
	SensorDto toDto(Sensor entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "sensorReadings", ignore = true)
	@Mapping(target = "sensorLans", ignore = true)
	@Mapping(target = "device", ignore = true)
	@Mapping(target = "sensorType", ignore = true)
	Sensor toEntity(CreateSensorDto dto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "sensorReadings", ignore = true)
	@Mapping(target = "sensorLans", ignore = true)
	@Mapping(target = "device", ignore = true)
	@Mapping(target = "sensorType", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "version", ignore = true)
	void updateEntityFromDto(@MappingTarget Sensor entity, UpdateSensorDto dto);
}
