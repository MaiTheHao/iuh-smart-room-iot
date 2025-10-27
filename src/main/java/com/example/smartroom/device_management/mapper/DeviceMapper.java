package com.example.smartroom.device_management.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import com.example.smartroom.device_management.dto.device.DeviceCreateDTO;
import com.example.smartroom.device_management.dto.device.DeviceDTO;
import com.example.smartroom.device_management.entity.Device;
import com.example.smartroom.device_management.entity.Hub;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DeviceMapper {

    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    @Mapping(target = "hubId", source = "hub.id")
    DeviceDTO toDTO(Device device);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "hub", source = "hub")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "location", source = "dto.location")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    Device toEntity(DeviceCreateDTO dto, Hub hub);
}
