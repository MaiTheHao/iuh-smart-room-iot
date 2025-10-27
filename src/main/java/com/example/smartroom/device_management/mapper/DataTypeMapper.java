package com.example.smartroom.device_management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.example.smartroom.device_management.dto.data_type.DataTypeCreateDTO;
import com.example.smartroom.device_management.dto.data_type.DataTypeDTO;
import com.example.smartroom.device_management.entity.DataType;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DataTypeMapper {

    DataTypeMapper INSTANCE = Mappers.getMapper(DataTypeMapper.class);

    DataTypeDTO toDTO(DataType entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    DataType toEntity(DataTypeCreateDTO createDTO);
}
