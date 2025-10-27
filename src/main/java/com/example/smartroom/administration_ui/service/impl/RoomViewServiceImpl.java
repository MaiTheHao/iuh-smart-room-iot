package com.example.smartroom.administration_ui.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.smartroom.administration_ui.dto.RoomListViewDataDTO;
import com.example.smartroom.administration_ui.dto.RoomDetailViewDataDTO;
import com.example.smartroom.administration_ui.mapper.RoomListViewDataMapper;
import com.example.smartroom.administration_ui.mapper.RoomDetailViewDataMapper;
import com.example.smartroom.administration_ui.service.RoomViewService;
import com.example.smartroom.device_management.dto.room.RoomDTO;
import com.example.smartroom.device_management.dto.hub.HubDTO;
import com.example.smartroom.device_management.service.RoomService;
import com.example.smartroom.device_management.service.HubService;
import com.example.smartroom.device_management.service.DeviceService;
import com.example.smartroom.device_management.service.SensorService;

@Service
@RequiredArgsConstructor
public class RoomViewServiceImpl implements RoomViewService {

    private final RoomService roomService;
    private final HubService hubService;
    private final DeviceService deviceService;
    private final SensorService sensorService;
    private final RoomListViewDataMapper roomListViewDataMapper;
    private final RoomDetailViewDataMapper roomDetailViewDataMapper;

    @Override
    public RoomListViewDataDTO getRoomListViewData(Pageable pageable) {
        Page<RoomDTO> rooms = roomService.getList(pageable);
        return roomListViewDataMapper.toDTO(rooms);
    }

    @Override
    public RoomDetailViewDataDTO getRoomDetailViewData(String roomId, Pageable pageable) {
        RoomDTO room = roomService.getRoomById(roomId);
        Page<HubDTO> hubs = hubService.getListByRoomId(roomId, pageable);
        Long totalHub = hubService.countByRoomId(roomId);
        Long totalDevice = deviceService.countByRoomId(roomId);
        Long totalSensor = sensorService.countByRoomId(roomId);

        return roomDetailViewDataMapper.toDTO(room, hubs, totalHub, totalDevice, totalSensor);
    }
}
