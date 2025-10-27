package com.example.smartroom.device_management.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.device_management.dto.device.DeviceCreateDTO;
import com.example.smartroom.device_management.dto.device.DeviceDTO;
import com.example.smartroom.device_management.entity.Device;
import com.example.smartroom.device_management.entity.Hub;
import com.example.smartroom.device_management.mapper.DeviceMapper;
import com.example.smartroom.device_management.repository.DeviceRepository;
import com.example.smartroom.device_management.repository.HubRepository;
import com.example.smartroom.device_management.service.DeviceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final HubRepository hubRepository;
    private final DeviceMapper deviceMapper;

    @Override
    @Transactional
    public DeviceDTO createDevice(DeviceCreateDTO dto) {
        Hub hub = hubRepository.findById(dto.getHubId())
            .orElseThrow(() -> new NotFoundException("Hub with id " + dto.getHubId() + " is not found"));

        if (deviceRepository.existsById(dto.getId())) throw new BadRequestException("Device with id " + dto.getId() + " already exists");
        
        Device device = deviceMapper.toEntity(dto, hub);
        Device savedDevice = deviceRepository.save(device);
        return deviceMapper.toDTO(savedDevice);
    }

    @Override
    public DeviceDTO getDeviceById(String id) {
        return deviceMapper.toDTO(
            deviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Device with id " + id + " is not found"))
        );
    }

    @Override
    public Page<DeviceDTO> getList(Pageable pageRequest) {
        return deviceRepository.findAll(pageRequest).map(deviceMapper::toDTO);
    }

    @Override
    public Page<DeviceDTO> getListByHubId(String hubId, Pageable pageRequest) {
        return deviceRepository.findAllByHubId(hubId, pageRequest).map(deviceMapper::toDTO);
    }

    @Override
    @Transactional
    public DeviceDTO deleteDeviceById(String id) {
        Device device = deviceRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Device with id " + id + " is not found"));
        deviceRepository.delete(device);
        return deviceMapper.toDTO(device);
    }

    @Override
    public Long count() {
        return deviceRepository.count();
    }

    @Override
    public Long countByHubId(String hubId) {
        return deviceRepository.countByHubId(hubId);
    }

    @Override
    public Long countByRoomId(String roomId) {
        return deviceRepository.countByRoomId(roomId);
    }
}
