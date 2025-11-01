package com.example.smartroom.device_management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.smartroom.device_management.dto.device.DeviceCreateDTO;
import com.example.smartroom.device_management.dto.device.DeviceDTO;
import com.example.smartroom.device_management.entity.Device;
import com.example.smartroom.device_management.entity.Hub;
import com.example.smartroom.device_management.entity.ConnectionType;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DeviceMapper {
    @Mapping(target = "hubId", source = "hub.id")
    @Mapping(target = "connectionTypeId", source = "connectionType.id")
    DeviceDTO toDTO(Device device);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "hub", source = "hub")
    @Mapping(target = "connectionType", source = "connectionType")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "location", source = "dto.location")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "sensors", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    Device toEntity(DeviceCreateDTO dto, Hub hub, ConnectionType connectionType);
}
