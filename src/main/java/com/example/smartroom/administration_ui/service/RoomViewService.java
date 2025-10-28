package com.example.smartroom.administration_ui.service;

import org.springframework.data.domain.Pageable;

import com.example.smartroom.administration_ui.dto.rooms.RoomDetailViewDataDTO;
import com.example.smartroom.administration_ui.dto.rooms.RoomListViewDataDTO;

public interface RoomViewService {
    RoomListViewDataDTO getRoomListViewData(Pageable pageable);
    RoomListViewDataDTO getRoomListViewData(String search, Pageable pageable);
    RoomDetailViewDataDTO getRoomDetailViewData(String roomId, Pageable pageable);
}
