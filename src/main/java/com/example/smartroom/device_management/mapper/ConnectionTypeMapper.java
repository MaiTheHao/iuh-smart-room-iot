package com.example.smartroom.device_management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.example.smartroom.device_management.dto.connection_type.ConnectionTypeCreateDTO;
import com.example.smartroom.device_management.dto.connection_type.ConnectionTypeDTO;
import com.example.smartroom.device_management.entity.ConnectionType;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ConnectionTypeMapper {

    ConnectionTypeMapper INSTANCE = Mappers.getMapper(ConnectionTypeMapper.class);

    ConnectionTypeDTO toDTO(ConnectionType connectionType);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    ConnectionType toEntity(ConnectionTypeCreateDTO createDTO);
}
