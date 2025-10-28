package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.device_management.dto.room.RoomCreateDTO;
import com.example.smartroom.device_management.dto.room.RoomDTO;
import com.example.smartroom.device_management.dto.room.RoomStatisticsDTO;

public interface RoomService {
    RoomDTO createRoom(RoomCreateDTO dto);
    RoomDTO getRoomById(String id);
    Page<RoomDTO> getList(Pageable pageRequest);
    Page<RoomDTO> getList(String search, Pageable pageable);
    RoomDTO deleteRoomById(String id);
    Long count();
    RoomStatisticsDTO getRoomStatisticsById(String id);
}
