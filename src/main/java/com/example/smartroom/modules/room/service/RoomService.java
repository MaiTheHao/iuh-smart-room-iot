package com.example.smartroom.modules.room.service;

import org.springframework.data.domain.Pageable;

import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.device.dto.DeviceDto;
import com.example.smartroom.modules.room.dto.CreateRoomDto;
import com.example.smartroom.modules.room.dto.RoomDto;
import com.example.smartroom.modules.room.dto.UpdateRoomDto;

public interface RoomService {
    RoomDto create(CreateRoomDto dto);
    RoomDto update(Long id, UpdateRoomDto dto, String langCode);
    void delete(Long id);
    RoomDto getById(Long id, String langCode);
    PaginationInfo<RoomDto> getList(Pageable pageable, String langCode);
    PaginationInfo<DeviceDto> getDevicesByRoomId(Long roomId, Pageable pageable, String langCode);
}
