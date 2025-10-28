package com.example.smartroom.administration_ui.mapper.hubs;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.example.smartroom.administration_ui.dto.hubs.HubDetailViewDataDTO;
import com.example.smartroom.device_management.dto.device.DeviceDTO;
import com.example.smartroom.device_management.dto.hub.HubDTO;

@Mapper(componentModel = "spring")
public interface HubDetailViewDataMapper {
    @Mapping(target = "hub", source = "hub")
    @Mapping(target = "devices", source = "devices")
    @Mapping(target = "totalDevice", source = "totalDevice")
    @Mapping(target = "totalSensor", source = "totalSensor")
    HubDetailViewDataDTO toDTO(
        HubDTO hub, 
        Page<DeviceDTO> devices, 
        Long totalDevice, 
        Long totalSensor
    );
}
