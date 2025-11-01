package com.example.smartroom.device_management.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.common.util.EntityMetadataUtil;
import com.example.smartroom.device_management.dto.room.RoomCreateDTO;
import com.example.smartroom.device_management.dto.room.RoomDTO;
import com.example.smartroom.device_management.dto.room.RoomStatisticsDTO;
import com.example.smartroom.device_management.entity.Room;
import com.example.smartroom.device_management.mapper.RoomMapper;
import com.example.smartroom.device_management.repository.RoomRepository;
import com.example.smartroom.device_management.service.RoomService;

import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Triển khai dịch vụ quản lý phòng, thực hiện các thao tác CRUD và thống kê.
 * Sử dụng Specification để tìm kiếm động dựa trên các trường có thể tìm kiếm.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService {
    
    private final EntityMetadataUtil entityMetadataUtil;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    @Transactional
    public RoomDTO createRoom(@Valid RoomCreateDTO dto) {
        if (roomRepository.findById(dto.getId()).isPresent()) {
            throw new BadRequestException("Room with id " + dto.getId() + " is existed");
        }
        // Xử lý lỗi: Kiểm tra trùng ID trước khi tạo, ném BadRequestException nếu tồn tại.
        Room newRoom = roomMapper.toEntity(dto);
        Room savedRoom = roomRepository.save(newRoom);
        return roomMapper.toDTO(savedRoom);
    }

    @Override
    public RoomDTO getRoomById(String id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room with id " + id + " not found"));
        return roomMapper.toDTO(room);
    }

    @Override
    public Page<RoomDTO> getList(Pageable pageRequest) {
        return roomRepository.findAll(pageRequest).map(roomMapper::toDTO);
    }

    @Override
    public Page<RoomDTO> getList(String search, Pageable pageable) {
        Specification<Room> spec = null;
        if (search != null && !search.isEmpty()) {
            String searchLower = search.toLowerCase();
            spec = (root, query, cb) -> cb.or(
                entityMetadataUtil.getMetadata(Room.class).getSearchableFields().stream()
                .map(prop -> cb.like(cb.lower(root.get(prop)), "%" + searchLower + "%"))
                .toArray(Predicate[]::new)
            );
        }
        // Tìm kiếm động: Sử dụng Specification để lọc các trường có thể tìm kiếm, không phân biệt hoa thường.
        return roomRepository.findAll(spec, pageable).map(roomMapper::toDTO);
    }

    @Override
    @Transactional
    public RoomDTO deleteRoomById(String id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room with id " + id + " not found"));
        roomRepository.delete(room);
        return roomMapper.toDTO(room);
    }

    @Override
    public Long count() {
        return roomRepository.count();
    }

    @Override
    public RoomStatisticsDTO getRoomStatisticsById(String id) {
        roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room with id " + id + " not found"));
        return roomRepository.getRoomStatisticsById(id);
    }
}
