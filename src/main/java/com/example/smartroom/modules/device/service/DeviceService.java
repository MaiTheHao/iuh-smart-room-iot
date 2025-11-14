package com.example.smartroom.modules.device.service;

import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.device.dto.CreateDeviceDto;
import com.example.smartroom.modules.device.dto.DeviceDto;
import com.example.smartroom.modules.device.dto.UpdateDeviceDto;
import org.springframework.data.domain.Pageable;

public interface DeviceService {
    DeviceDto create(CreateDeviceDto dto);
    DeviceDto update(Long id, UpdateDeviceDto dto, String langCode);
    void delete(Long id);
    DeviceDto getById(Long id, String langCode);
    PaginationInfo<DeviceDto> getList(Pageable pageable, String langCode);
    PaginationInfo<DeviceDto> getGateways(Pageable pageable, String langCode);
    PaginationInfo<DeviceDto> getByGatewayId(Long gatewayId, Pageable pageable, String langCode);
}
