package com.example.smartroom.device_management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.smartroom.device_management.dto.sensor.SensorCreateDTO;
import com.example.smartroom.device_management.dto.sensor.SensorDTO;
import com.example.smartroom.device_management.entity.Sensor;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SensorMapper {
    @Mapping(source = "device.id", target = "deviceId")
    SensorDTO toDTO(Sensor sensor);
    
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "sensorDatas", ignore = true)
    @Mapping(target = "sensorDataTypes", ignore = true)
    Sensor toEntity(SensorCreateDTO createDTO);
}
