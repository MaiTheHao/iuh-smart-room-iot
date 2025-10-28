package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.device_management.dto.device.DeviceCreateDTO;
import com.example.smartroom.device_management.dto.device.DeviceDTO;
import com.example.smartroom.device_management.dto.device.DeviceStatisticsDTO;

public interface DeviceService {
    DeviceDTO createDevice(DeviceCreateDTO dto);
    DeviceDTO getDeviceById(String id);
    Page<DeviceDTO> getList(Pageable pageRequest);
    Page<DeviceDTO> getListByHubId(String hubId, Pageable pageRequest);
    DeviceDTO deleteDeviceById(String id);
    Long count();
    Long countByHubId(String hubId);
    Long countByRoomId(String roomId);
    DeviceStatisticsDTO getDeviceStatisticsById(String id);
}
