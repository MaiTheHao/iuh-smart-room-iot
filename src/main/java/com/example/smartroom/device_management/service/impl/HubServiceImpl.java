package com.example.smartroom.device_management.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.device_management.dto.hub.HubCreateDTO;
import com.example.smartroom.device_management.dto.hub.HubDTO;
import com.example.smartroom.device_management.dto.hub.HubStatisticsDTO;
import com.example.smartroom.device_management.entity.Hub;
import com.example.smartroom.device_management.entity.Room;
import com.example.smartroom.device_management.mapper.HubMapper;
import com.example.smartroom.device_management.repository.HubRepository;
import com.example.smartroom.device_management.repository.RoomRepository;
import com.example.smartroom.device_management.service.HubService;

import lombok.RequiredArgsConstructor;

/**
 * Triển khai dịch vụ quản lý hub, thực hiện các thao tác CRUD và thống kê.
 * Liên kết với phòng để đảm bảo tính toàn vẹn dữ liệu.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubServiceImpl implements HubService {
    private final HubRepository hubRepository;
    private final RoomRepository roomRepository;
    private final HubMapper hubMapper;

    @Override
    @Transactional
    public HubDTO createHub(HubCreateDTO dto) {
        Room room = roomRepository.findById(dto.getRoomId())
            .orElseThrow(() -> new NotFoundException("Room with id " + dto.getRoomId() + " is not found"));

        if (hubRepository.existsById(dto.getId())) throw new BadRequestException("Hub with id " + dto.getId() + " already exists");
        // Xử lý lỗi: Kiểm tra phòng tồn tại và hub không trùng ID, ném ngoại lệ tương ứng.
        Hub newHub = hubMapper.toEntity(dto, room);
        Hub savedHub = hubRepository.save(newHub);
        return hubMapper.toDTO(savedHub);
    }

    @Override
    public HubDTO getHubById(String id) {
        return hubRepository.findById(id).map(hubMapper::toDTO)
            .orElseThrow(() -> new NotFoundException("Hub with id " + id + " is not found"));
    }

    @Override
    public Page<HubDTO> getList(Pageable pageRequest) {
        return hubRepository.findAll(pageRequest).map(hubMapper::toDTO);
    }

    @Override
    public Page<HubDTO> getListByRoomId(String roomId, Pageable pageRequest) {
        roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Room with id " + roomId + " is not found"));
        return hubRepository.findByRoomId(roomId, pageRequest).map(hubMapper::toDTO);
    }

    @Override
    @Transactional
    public HubDTO deleteHubById(String id) {
        Hub hub = hubRepository.findById(id).orElseThrow(() -> new NotFoundException("Hub with id " + id + " is not found"));
        hubRepository.delete(hub);
        return hubMapper.toDTO(hub);
    }

    @Override
    public Long count() {
        return hubRepository.count();
    }

    @Override
    public Long countByRoomId(String roomId) {
        roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Room with id " + roomId + " is not found"));
        return hubRepository.countByRoomId(roomId);
    }

    @Override
    public HubStatisticsDTO getHubStatisticsById(String id) {
        hubRepository.findById(id).orElseThrow(() -> new NotFoundException("Hub with id " + id + " is not found"));
        return hubRepository.getHubStatisticsById(id);
    }
}
