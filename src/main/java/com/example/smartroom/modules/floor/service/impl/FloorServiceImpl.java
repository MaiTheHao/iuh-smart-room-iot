package com.example.smartroom.modules.floor.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.smartroom.core.common.exception.InternalServerException;
import com.example.smartroom.core.common.exception.NotFoundException;
import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.floor.dto.CreateFloorDto;
import com.example.smartroom.modules.floor.dto.FloorDto;
import com.example.smartroom.modules.floor.dto.UpdateFloorDto;
import com.example.smartroom.modules.floor.entity.Floor;
import com.example.smartroom.modules.floor.entity.FloorLan;
import com.example.smartroom.modules.floor.mapper.FloorMapper;
import com.example.smartroom.modules.floor.repository.FloorRepository;
import com.example.smartroom.modules.floor.service.FloorService;
import com.example.smartroom.modules.room.dto.RoomDto;
import com.example.smartroom.modules.room.entity.Room;
import com.example.smartroom.modules.room.entity.RoomLan;
import com.example.smartroom.modules.room.mapper.RoomMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FloorServiceImpl implements FloorService {
    private final FloorRepository floorRepository;
    private final FloorMapper floorMapper;
    private final RoomMapper roomMapper;

    @Override
    @Transactional
    public FloorDto create(CreateFloorDto dto) {
        Floor floor = floorMapper.toEntity(dto);
        floor = floorRepository.save(floor);
        
        FloorDto result = floorMapper.toDto(floor);
        return result;
    }

    @Override
    @Transactional
    public FloorDto update(Long id, UpdateFloorDto dto, String langCode) {
        Floor floor = floorRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Floor not found with id: " + id));
        
        if (dto.getLevel() != null) {
            floor.setLevel(dto.getLevel());
        }
        
        floor = floorRepository.save(floor);
        FloorDto result = floorMapper.toDto(floor);
        
        if (langCode != null) {
            populateTranslationFields(result, floor, langCode);
        }
        
        return result;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!floorRepository.existsById(id)) {
            throw new NotFoundException("Floor not found with id: " + id);
        }
        try {
            floorRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete floor", ex);
        }
    }

    @Override
    public FloorDto getById(Long id, String langCode) {
        Floor floor = floorRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Floor not found with id: " + id));
        
        FloorDto result = floorMapper.toDto(floor);
        
        if (langCode != null) {
            populateTranslationFields(result, floor, langCode);
        }
        
        return result;
    }

    @Override
    public PaginationInfo<FloorDto> getList(Pageable pageable, String langCode) {
        Page<Floor> floorPage = floorRepository.findAll(pageable);
        
        List<FloorDto> dtoList = floorPage.getContent().stream()
            .map(floor -> {
                FloorDto dto = floorMapper.toDto(floor);
                
                if (langCode != null) {
                    populateTranslationFields(dto, floor, langCode);
                }
                
                return dto;
            })
            .toList();
        
        return PaginationInfo.from(floorPage, dtoList);
    }

    @Override
    public PaginationInfo<RoomDto> getRoomsByFloorId(Long floorId, Pageable pageable, String langCode) {
        // Verify floor exists
        if (!floorRepository.existsById(floorId)) {
            throw new NotFoundException("Floor not found with id: " + floorId);
        }

        Page<Room> roomPage = floorRepository.findRoomsByFloorId(floorId, pageable);

        List<RoomDto> dtoList = roomPage.getContent().stream()
            .map(room -> {
                RoomDto dto = roomMapper.toDto(room);
                
                if (langCode != null) {
                    populateRoomTranslationFields(dto, room, langCode);
                }
                
                return dto;
            })
            .toList();

        return PaginationInfo.from(roomPage, dtoList);
    }

    private void populateTranslationFields(FloorDto dto, Floor floor, String langCode) {
        FloorLan matchedLan = floor.getFloorLans().stream()
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

    private void populateRoomTranslationFields(RoomDto dto, Room room, String langCode) {
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
}
