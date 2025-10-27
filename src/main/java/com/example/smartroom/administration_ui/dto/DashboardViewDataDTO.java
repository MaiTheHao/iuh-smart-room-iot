package com.example.smartroom.administration_ui.dto;

import org.springframework.data.domain.Page;

import com.example.smartroom.device_management.dto.device.DeviceDTO;
import com.example.smartroom.device_management.dto.hub.HubDTO;
import com.example.smartroom.device_management.dto.room.RoomDTO;
import com.example.smartroom.device_management.dto.sensor.SensorDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class DashboardViewDataDTO {

    Page<RoomDTO> rooms;

    Page<HubDTO> hubs;

    Page<DeviceDTO> devices;

    Page<SensorDTO> sensors;
}
