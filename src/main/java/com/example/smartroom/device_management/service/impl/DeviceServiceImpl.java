package com.example.smartroom.device_management.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.device_management.dto.device.DeviceCreateDTO;
import com.example.smartroom.device_management.dto.device.DeviceDTO;
import com.example.smartroom.device_management.dto.device.DeviceStatisticsDTO;
import com.example.smartroom.device_management.entity.ConnectionType;
import com.example.smartroom.device_management.entity.Device;
import com.example.smartroom.device_management.entity.Hub;
import com.example.smartroom.device_management.mapper.DeviceMapper;
import com.example.smartroom.device_management.repository.ConnectionTypeRepository;
import com.example.smartroom.device_management.repository.DeviceRepository;
import com.example.smartroom.device_management.repository.HubRepository;
import com.example.smartroom.device_management.repository.RoomRepository;
import com.example.smartroom.device_management.service.DeviceService;

import lombok.RequiredArgsConstructor;

/**
 * Triển khai dịch vụ quản lý thiết bị, thực hiện các thao tác CRUD và thống kê.
 * Liên kết với hub và loại kết nối để đảm bảo tính toàn vẹn.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final HubRepository hubRepository;
    private final RoomRepository roomRepository;
    private final ConnectionTypeRepository connectionTypeRepository;
    private final DeviceMapper deviceMapper;

    @Override
    @Transactional
    public DeviceDTO createDevice(DeviceCreateDTO dto) {
        Hub hub = hubRepository.findById(dto.getHubId())
            .orElseThrow(() -> new NotFoundException("Hub with id " + dto.getHubId() + " is not found"));

        ConnectionType connectionType = connectionTypeRepository.findByCode(dto.getConnectionTypeCode())
            .orElseThrow(() -> new NotFoundException("ConnectionType with code " + dto.getConnectionTypeCode() + " is not found"));

        if (deviceRepository.existsById(dto.getId())) throw new BadRequestException("Device with id " + dto.getId() + " already exists");
        // Xử lý lỗi: Kiểm tra hub, loại kết nối tồn tại và thiết bị không trùng ID, ném ngoại lệ tương ứng.
        Device device = deviceMapper.toEntity(dto, hub, connectionType);
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
        hubRepository.findById(hubId).orElseThrow(() -> new NotFoundException("Hub with id " + hubId + " is not found"));
        return deviceRepository.findAllByHubId(hubId, pageRequest).map(deviceMapper::toDTO);
    }

    @Override
    @Transactional
    public DeviceDTO deleteDeviceById(String id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Device with id " + id + " is not found"));
        deviceRepository.delete(device);
        return deviceMapper.toDTO(device);
    }

    @Override
    public Long count() {
        return deviceRepository.count();
    }

    @Override
    public Long countByHubId(String hubId) {
        hubRepository.findById(hubId).orElseThrow(() -> new NotFoundException("Hub with id " + hubId + " is not found"));
        return deviceRepository.countByHubId(hubId);
    }

    @Override
    public Long countByRoomId(String roomId) {
        roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Room with id " + roomId + " is not found"));
        return deviceRepository.countByRoomId(roomId);
    }

    @Override
    public DeviceStatisticsDTO getDeviceStatisticsById(String id) {
        deviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Device with id " + id + " is not found"));
        return deviceRepository.getDeviceStatisticsById(id);
    }
}
