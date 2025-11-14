package com.example.smartroom.modules.room.service;

import java.util.List;

import com.example.smartroom.modules.room.dto.CreateRoomLanDto;
import com.example.smartroom.modules.room.dto.RoomLanDto;
import com.example.smartroom.modules.room.dto.UpdateRoomLanDto;

public interface RoomLanService {
    RoomLanDto create(CreateRoomLanDto dto);
    RoomLanDto update(Long id, UpdateRoomLanDto dto);
    void delete(Long id);
    RoomLanDto getById(Long id);
    List<RoomLanDto> getByRoomId(Long roomId);
    RoomLanDto getByRoomIdAndLangCode(Long roomId, String langCode);
    RoomLanDto upsertByRoomIdAndLangCode(Long roomId, String langCode, UpdateRoomLanDto dto);
    void deleteByRoomIdAndLangCode(Long roomId, String langCode);
}
