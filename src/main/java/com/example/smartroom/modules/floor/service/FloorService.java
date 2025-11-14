package com.example.smartroom.modules.floor.service;

import org.springframework.data.domain.Pageable;

import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.floor.dto.CreateFloorDto;
import com.example.smartroom.modules.floor.dto.FloorDto;
import com.example.smartroom.modules.floor.dto.UpdateFloorDto;
import com.example.smartroom.modules.room.dto.RoomDto;

public interface FloorService {
    FloorDto create(CreateFloorDto dto);
    FloorDto update(Long id, UpdateFloorDto dto, String langCode);
    void delete(Long id);
    FloorDto getById(Long id, String langCode);
    PaginationInfo<FloorDto> getList(Pageable pageable, String langCode);
    PaginationInfo<RoomDto> getRoomsByFloorId(Long floorId, Pageable pageable, String langCode);
}
