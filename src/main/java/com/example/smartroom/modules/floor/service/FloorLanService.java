package com.example.smartroom.modules.floor.service;

import java.util.List;

import com.example.smartroom.modules.floor.dto.CreateFloorLanDto;
import com.example.smartroom.modules.floor.dto.FloorLanDto;
import com.example.smartroom.modules.floor.dto.UpdateFloorLanDto;

public interface FloorLanService {
    FloorLanDto create(CreateFloorLanDto dto);
    FloorLanDto update(Long id, UpdateFloorLanDto dto);
    void delete(Long id);
    FloorLanDto getById(Long id);
    List<FloorLanDto> getByFloorId(Long floorId);
    FloorLanDto getByFloorIdAndLangCode(Long floorId, String langCode);
    FloorLanDto upsertByFloorIdAndLangCode(Long floorId, String langCode, UpdateFloorLanDto dto);
    void deleteByFloorIdAndLangCode(Long floorId, String langCode);
}
