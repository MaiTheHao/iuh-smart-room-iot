package com.example.smartroom.administration_ui.service.impl;

import com.example.smartroom.administration_ui.dto.DashboardViewDataDTO;
import com.example.smartroom.administration_ui.mapper.DashboardViewDataMapper;
import com.example.smartroom.administration_ui.service.DashboardService;
import com.example.smartroom.device_management.service.RoomService;
import com.example.smartroom.device_management.service.HubService;
import com.example.smartroom.device_management.service.DeviceService;
import com.example.smartroom.device_management.service.SensorService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.smartroom.device_management.dto.room.RoomDTO;
import com.example.smartroom.device_management.dto.hub.HubDTO;
import com.example.smartroom.device_management.dto.device.DeviceDTO;
import com.example.smartroom.device_management.dto.sensor.SensorDTO;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final RoomService roomService;
    private final HubService hubService;
    private final DeviceService deviceService;
    private final SensorService sensorService;
    private final DashboardViewDataMapper dashboardDataMapper;

    @Override
    public DashboardViewDataDTO getDashboardData(Pageable roomPageable, Pageable hubPageable, Pageable devicePageable, Pageable sensorPageable) {
        Page<RoomDTO> rooms = roomService.getList(roomPageable);
        Page<HubDTO> hubs = hubService.getList(hubPageable);
        Page<DeviceDTO> devices = deviceService.getList(devicePageable);
        Page<SensorDTO> sensors = sensorService.getList(sensorPageable);

        return dashboardDataMapper.toDTO(rooms, hubs, devices, sensors);
    }

    @Override
    public DashboardViewDataDTO getDashboardData(Pageable pageable) {
        Page<RoomDTO> rooms = roomService.getList(pageable);
        Page<HubDTO> hubs = hubService.getList(pageable);
        Page<DeviceDTO> devices = deviceService.getList(pageable);
        Page<SensorDTO> sensors = sensorService.getList(pageable);

        return dashboardDataMapper.toDTO(rooms, hubs, devices, sensors);
    }
}
