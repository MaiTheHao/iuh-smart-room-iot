package com.example.smartroom.administration_ui.service;

import org.springframework.data.domain.Pageable;
import com.example.smartroom.administration_ui.dto.RoomListViewDataDTO;
import com.example.smartroom.administration_ui.dto.RoomDetailViewDataDTO;

public interface RoomViewService {
    RoomListViewDataDTO getRoomListViewData(Pageable pageable);
    RoomDetailViewDataDTO getRoomDetailViewData(String roomId, Pageable pageable);
}
