package com.example.smartroom.modules.room.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.smartroom.core.common.exception.InternalServerException;
import com.example.smartroom.core.common.exception.NotFoundException;
import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.device.dto.DeviceDto;
import com.example.smartroom.modules.device.entity.Device;
import com.example.smartroom.modules.device.entity.DeviceLan;
import com.example.smartroom.modules.device.mapper.DeviceMapper;
import com.example.smartroom.modules.floor.entity.Floor;
import com.example.smartroom.modules.floor.repository.FloorRepository;
import com.example.smartroom.modules.room.dto.CreateRoomDto;
import com.example.smartroom.modules.room.dto.RoomDto;
import com.example.smartroom.modules.room.dto.UpdateRoomDto;
import com.example.smartroom.modules.room.entity.Room;
import com.example.smartroom.modules.room.entity.RoomLan;
import com.example.smartroom.modules.room.mapper.RoomMapper;
import com.example.smartroom.modules.room.repository.RoomRepository;
import com.example.smartroom.modules.room.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final FloorRepository floorRepository;
    private final RoomMapper roomMapper;
    private final DeviceMapper deviceMapper;

    @Override
    @Transactional
    public RoomDto create(CreateRoomDto dto) {
        Floor floor = floorRepository.findById(dto.getFloorId())
            .orElseThrow(() -> new NotFoundException("Floor not found with id: " + dto.getFloorId()));
        
        Room room = roomMapper.toEntity(dto);
        room.setFloor(floor);
        room = roomRepository.save(room);
        
        RoomDto result = roomMapper.toDto(room);
        return result;
    }

    @Override
    @Transactional
    public RoomDto update(Long id, UpdateRoomDto dto, String langCode) {
        Room room = roomRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Room not found with id: " + id));

        if (dto.getFloorId() != null) {
            Floor floor = floorRepository.findById(dto.getFloorId())
                .orElseThrow(() -> new NotFoundException("Floor not found with id: " + dto.getFloorId()));
            room.setFloor(floor);
        }
        
        room = roomRepository.save(room);
        RoomDto result = roomMapper.toDto(room);
        
        if (langCode != null) {
            populateTranslationFields(result, room, langCode);
        }
        
        return result;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new NotFoundException("Room not found with id: " + id);
        }
        try {
            roomRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete room", ex);
        }
    }

    @Override
    public RoomDto getById(Long id, String langCode) {
        Room room = roomRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Room not found with id: " + id));
        
        RoomDto result = roomMapper.toDto(room);
        
        if (langCode != null) {
            populateTranslationFields(result, room, langCode);
        }
        
        return result;
    }

    @Override
    public PaginationInfo<RoomDto> getList(Pageable pageable, String langCode) {
        Page<Room> roomPage = roomRepository.findAll(pageable);
        
        List<RoomDto> dtoList = roomPage.getContent().stream()
            .map(room -> {
                RoomDto dto = roomMapper.toDto(room);
                
                if (langCode != null) {
                    populateTranslationFields(dto, room, langCode);
                }
                
                return dto;
            })
            .toList();
        
        return PaginationInfo.from(roomPage, dtoList);
    }
    
    @Override
    public PaginationInfo<DeviceDto> getDevicesByRoomId(Long roomId, Pageable pageable, String langCode) {
        // Verify room exists
        if (!roomRepository.existsById(roomId)) {
            throw new NotFoundException("Room not found with id: " + roomId);
        }

        Page<Device> devicePage = roomRepository.findDevicesByRoomId(roomId, pageable);

        List<DeviceDto> dtoList = devicePage.getContent().stream()
            .map(device -> {
                DeviceDto dto = deviceMapper.toDto(device);
                
                if (langCode != null) {
                    populateDeviceTranslationFields(dto, device, langCode);
                }
                
                return dto;
            })
            .toList();

        return PaginationInfo.from(devicePage, dtoList);
    }

    private void populateTranslationFields(RoomDto dto, Room room, String langCode) {
        RoomLan matchedLan = room.getRoomLans().stream()
            .filter(lan -> lan.getLanguage().getCode().equalsIgnoreCase(langCode))
            .findFirst()
            .orElse(null);
        
        if (matchedLan != null) {
            dto.setName(matchedLan.getName());
            dto.setDescription(matchedLan.getDescription());
            dto.setLocationDescription(matchedLan.getLocationDescription());
            dto.setLangCode(matchedLan.getLanguage().getCode());
        }
    }

    private void populateDeviceTranslationFields(DeviceDto dto, Device device, String langCode) {
        if (device.getDeviceLans() == null) return;

        DeviceLan matchedLan = device.getDeviceLans().stream()
            .filter(lan -> lan.getLanguage().getCode().equalsIgnoreCase(langCode))
            .findFirst()
            .orElse(null);
        
        if (matchedLan != null) {
            dto.setName(matchedLan.getName());
            dto.setDescription(matchedLan.getDescription());
            dto.setLocationDescription(matchedLan.getLocationDescription());
            dto.setLangCode(matchedLan.getLanguage().getCode());
        }
    }
}
