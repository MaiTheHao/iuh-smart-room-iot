package com.example.smartroom.device_management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.example.smartroom.device_management.dto.sensor_data_type.SensorDataTypeCreateDTO;
import com.example.smartroom.device_management.dto.sensor_data_type.SensorDataTypeDTO;
import com.example.smartroom.device_management.entity.SensorDataType;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SensorDataTypeMapper {

    SensorDataTypeMapper INSTANCE = Mappers.getMapper(SensorDataTypeMapper.class);

    @Mapping(target = "sensorId", source = "entity.sensor.id")
    @Mapping(target = "dataTypeId", source = "entity.dataType.id")
    @Mapping(target = "dataTypeCode", source = "entity.dataType.code")
    @Mapping(target = "dataTypeName", source = "entity.dataType.name")
    @Mapping(target = "dataTypeUnit", source = "entity.dataType.unit")
    SensorDataTypeDTO toDTO(SensorDataType entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sensor", ignore = true)
    @Mapping(target = "dataType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    SensorDataType toEntity(SensorDataTypeCreateDTO createDTO);
}
