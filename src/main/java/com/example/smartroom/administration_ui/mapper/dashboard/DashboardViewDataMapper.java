package com.example.smartroom.administration_ui.mapper.dashboard;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.example.smartroom.administration_ui.dto.dashboard.DashboardViewDataDTO;
import com.example.smartroom.device_management.dto.device.DeviceDTO;
import com.example.smartroom.device_management.dto.hub.HubDTO;
import com.example.smartroom.device_management.dto.room.RoomDTO;
import com.example.smartroom.device_management.dto.sensor.SensorDTO;

@Mapper(componentModel = "spring")
public interface DashboardViewDataMapper {
    DashboardViewDataDTO toDTO(
        Page<RoomDTO> rooms,
        Page<HubDTO> hubs,
        Page<DeviceDTO> devices,
        Page<SensorDTO> sensors
    );
}
