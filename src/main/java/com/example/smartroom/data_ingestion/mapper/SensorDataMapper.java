package com.example.smartroom.data_ingestion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.example.smartroom.data_ingestion.dto.sensor_data.SensorDataCreateDTO;
import com.example.smartroom.data_ingestion.dto.sensor_data.SensorDataDTO;
import com.example.smartroom.data_ingestion.entity.SensorData;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SensorDataMapper {

    SensorDataMapper INSTANCE = Mappers.getMapper(SensorDataMapper.class);

    @Mapping(source = "sensor.id", target = "sensorId")
    @Mapping(source = "sensor.name", target = "sensorName")
    @Mapping(source = "dataType.id", target = "dataTypeId")
    @Mapping(source = "dataType.name", target = "dataTypeName")
    @Mapping(source = "dataType.unit", target = "dataTypeUnit")
    SensorDataDTO toDTO(SensorData sensorData);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sensor", ignore = true)
    @Mapping(target = "dataType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SensorData toEntity(SensorDataCreateDTO createDTO);
}
