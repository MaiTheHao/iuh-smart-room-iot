package com.example.smartroom.modules.device.mapper;

import com.example.smartroom.modules.device.dto.CreateDeviceLanDto;
import com.example.smartroom.modules.device.dto.DeviceLanDto;
import com.example.smartroom.modules.device.dto.UpdateDeviceLanDto;
import com.example.smartroom.modules.device.entity.DeviceLan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeviceLanMapper {
    DeviceLanMapper INSTANCE = Mappers.getMapper(DeviceLanMapper.class);

    @Mapping(target = "langCode", source = "language.code")
    DeviceLanDto toDto(DeviceLan entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "language", ignore = true)
    DeviceLan toEntity(CreateDeviceLanDto dto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(@MappingTarget DeviceLan entity, UpdateDeviceLanDto dto);
}
