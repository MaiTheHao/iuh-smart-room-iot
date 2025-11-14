package com.example.smartroom.modules.device.service;

import com.example.smartroom.modules.device.dto.CreateDeviceLanDto;
import com.example.smartroom.modules.device.dto.DeviceLanDto;
import com.example.smartroom.modules.device.dto.UpdateDeviceLanDto;

import java.util.List;

public interface DeviceLanService {
    DeviceLanDto create(CreateDeviceLanDto dto);
    DeviceLanDto update(Long id, UpdateDeviceLanDto dto);
    void delete(Long id);
    DeviceLanDto getById(Long id);
    List<DeviceLanDto> getByDeviceId(Long deviceId);
    DeviceLanDto getByDeviceIdAndLangCode(Long deviceId, String langCode);
    DeviceLanDto upsertByDeviceIdAndLangCode(Long deviceId, String langCode, UpdateDeviceLanDto dto);
    void deleteByDeviceIdAndLangCode(Long deviceId, String langCode);
}
