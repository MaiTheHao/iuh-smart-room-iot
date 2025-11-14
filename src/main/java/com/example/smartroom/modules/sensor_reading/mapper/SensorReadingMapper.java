package com.example.smartroom.modules.sensor_reading.mapper;

import com.example.smartroom.modules.sensor_reading.dto.CreateSensorReadingDto;
import com.example.smartroom.modules.sensor_reading.dto.SensorReadingDto;
import com.example.smartroom.modules.sensor_reading.entity.SensorReading;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SensorReadingMapper {
    SensorReadingMapper INSTANCE = Mappers.getMapper(SensorReadingMapper.class);

    @Mapping(target = "sensorId", source = "sensor.id")
    SensorReadingDto toDto(SensorReading entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sensor", ignore = true)
    SensorReading toEntity(CreateSensorReadingDto dto);
}
