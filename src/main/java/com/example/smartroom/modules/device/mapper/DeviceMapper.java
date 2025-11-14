package com.example.smartroom.modules.device.mapper;

import com.example.smartroom.modules.device.dto.CreateDeviceDto;
import com.example.smartroom.modules.device.dto.DeviceDto;
import com.example.smartroom.modules.device.dto.UpdateDeviceDto;
import com.example.smartroom.modules.device.entity.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);
    
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "gatewayId", source = "gateway.id")
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "locationDescription", ignore = true)
    @Mapping(target = "langCode", ignore = true)
    DeviceDto toDto(Device entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "gateway", ignore = true)
    @Mapping(target = "sensors", ignore = true)
    @Mapping(target = "deviceLans", ignore = true)
    @Mapping(target = "childDevices", ignore = true)
    Device toEntity(CreateDeviceDto dto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "gateway", ignore = true)
    @Mapping(target = "sensors", ignore = true)
    @Mapping(target = "deviceLans", ignore = true)
    @Mapping(target = "childDevices", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(@MappingTarget Device entity, UpdateDeviceDto dto);
}
