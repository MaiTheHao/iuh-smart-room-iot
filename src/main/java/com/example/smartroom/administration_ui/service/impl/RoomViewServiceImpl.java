package com.example.smartroom.administration_ui.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.smartroom.administration_ui.dto.rooms.RoomDetailViewDataDTO;
import com.example.smartroom.administration_ui.dto.rooms.RoomListViewDataDTO;
import com.example.smartroom.administration_ui.mapper.rooms.RoomDetailViewDataMapper;
import com.example.smartroom.administration_ui.mapper.rooms.RoomListViewDataMapper;
import com.example.smartroom.administration_ui.service.RoomViewService;
import com.example.smartroom.device_management.dto.room.RoomDTO;
import com.example.smartroom.device_management.dto.room.RoomStatisticsDTO;
import com.example.smartroom.device_management.dto.hub.HubDTO;
import com.example.smartroom.device_management.service.RoomService;
import com.example.smartroom.device_management.service.HubService;

@Service
@RequiredArgsConstructor
public class RoomViewServiceImpl implements RoomViewService {

    private final RoomService roomService;
    private final HubService hubService;
    private final RoomListViewDataMapper roomListViewDataMapper;
    private final RoomDetailViewDataMapper roomDetailViewDataMapper;

    @Override
    public RoomListViewDataDTO getRoomListViewData(Pageable pageable) {
        Page<RoomDTO> rooms = roomService.getList(pageable);
        return roomListViewDataMapper.toDTO(rooms);
    }

    @Override
    public RoomListViewDataDTO getRoomListViewData(String search, Pageable pageable) {
        Page<RoomDTO> rooms = roomService.getList(search, pageable);
        return roomListViewDataMapper.toDTO(rooms);
    }

    @Override
    public RoomDetailViewDataDTO getRoomDetailViewData(String roomId, Pageable pageable) {
        RoomStatisticsDTO roomStats = roomService.getRoomStatisticsById(roomId);
        Page<HubDTO> hubsPage = hubService.getListByRoomId(roomId, pageable);
        Long totalHub = roomStats.hubCount();
        Long totalDevice = roomStats.deviceCount();
        Long totalSensor = roomStats.sensorCount();
        return roomDetailViewDataMapper.toDTO(roomStats, hubsPage, totalHub, totalDevice, totalSensor);
    }
}
