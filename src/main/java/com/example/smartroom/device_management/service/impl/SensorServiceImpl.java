package com.example.smartroom.device_management.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.device_management.dto.sensor.SensorCreateDTO;
import com.example.smartroom.device_management.dto.sensor.SensorDTO;
import com.example.smartroom.device_management.dto.sensor.SensorStatisticsDTO;
import com.example.smartroom.device_management.entity.Device;
import com.example.smartroom.device_management.entity.Sensor;
import com.example.smartroom.device_management.mapper.SensorMapper;
import com.example.smartroom.device_management.repository.DeviceRepository;
import com.example.smartroom.device_management.repository.HubRepository;
import com.example.smartroom.device_management.repository.RoomRepository;
import com.example.smartroom.device_management.repository.SensorRepository;
import com.example.smartroom.device_management.service.SensorService;

import lombok.RequiredArgsConstructor;

/**
 * Triển khai dịch vụ quản lý cảm biến, thực hiện các thao tác CRUD và thống kê.
 * Liên kết với thiết bị để đảm bảo tính toàn vẹn.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SensorServiceImpl implements SensorService {
    private final SensorRepository sensorRepository;
    private final DeviceRepository deviceRepository;
    private final HubRepository hubRepository;
    private final RoomRepository roomRepository;
    private final SensorMapper sensorMapper;

    @Override
    @Transactional
    public SensorDTO createSensor(SensorCreateDTO dto) {
        Device device = deviceRepository.findById(dto.getDeviceId())
            .orElseThrow(() -> new NotFoundException("Device with id " + dto.getDeviceId() + " not found"));

        if (sensorRepository.existsById(dto.getId())) throw new BadRequestException("Sensor with id " + dto.getId() + " already exists");
        // Xử lý lỗi: Kiểm tra thiết bị tồn tại và cảm biến không trùng ID, ném ngoại lệ tương ứng.
        Sensor sensor = sensorMapper.toEntity(dto);
        sensor.setDevice(device);
        return sensorMapper.toDTO(sensorRepository.save(sensor));
    }

    @Override
    public SensorDTO getSensorById(String id) {
        Sensor sensor = sensorRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Sensor id " + id + " not found"));

        return sensorMapper.toDTO(sensor);
    }

    @Override
    public Page<SensorDTO> getList(Pageable pageRequest) {
        return sensorRepository.findAll(pageRequest).map(sensorMapper::toDTO);
    }

    @Override
    public Page<SensorDTO> getListByDeviceId(String deviceId, Pageable pageRequest) {
        deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException("Device with id " + deviceId + " not found"));
        return sensorRepository.findByDeviceId(deviceId, pageRequest).map(sensorMapper::toDTO);
    }

    @Override
    @Transactional
    public SensorDTO deleteSensorById(String id) {
        Sensor sensor = sensorRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Sensor id " + id + " not found"));

        sensorRepository.delete(sensor);
        return sensorMapper.toDTO(sensor);
    }

    @Override
    public Long count() {
        return sensorRepository.count();
    }

    @Override
    public Long countByDeviceId(String deviceId) throws NotFoundException {
        deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException("Device with id " + deviceId + " not found"));
        return sensorRepository.countByDeviceId(deviceId);
    }

    @Override
    public Long countByHubId(String hubId) throws NotFoundException {
        hubRepository.findById(hubId).orElseThrow(() -> new NotFoundException("Hub with id " + hubId + " not found"));
        return sensorRepository.countByHubId(hubId);
    }

    @Override
    public Long countByRoomId(String roomId) throws NotFoundException {
        roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Room with id " + roomId + " not found"));
        return sensorRepository.countByRoomId(roomId);
    }

    @Override
    public SensorStatisticsDTO getSensorStatisticsById(String id) throws NotFoundException {
        sensorRepository.findById(id).orElseThrow(() -> new NotFoundException("Sensor id " + id + " not found"));
        return sensorRepository.getSensorStatisticsById(id);
    }
    
}
