package com.example.smartroom.modules.room.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.smartroom.core.common.exception.InternalServerException;
import com.example.smartroom.core.common.exception.NotFoundException;
import com.example.smartroom.modules.language.entity.Language;
import com.example.smartroom.modules.language.repository.LanguageRepository;
import com.example.smartroom.modules.room.dto.CreateRoomLanDto;
import com.example.smartroom.modules.room.dto.RoomLanDto;
import com.example.smartroom.modules.room.dto.UpdateRoomLanDto;
import com.example.smartroom.modules.room.entity.Room;
import com.example.smartroom.modules.room.entity.RoomLan;
import com.example.smartroom.modules.room.mapper.RoomLanMapper;
import com.example.smartroom.modules.room.repository.RoomLanRepository;
import com.example.smartroom.modules.room.repository.RoomRepository;
import com.example.smartroom.modules.room.service.RoomLanService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomLanServiceImpl implements RoomLanService {
    
    private final RoomLanRepository roomLanRepository;
    private final RoomRepository roomRepository;
    private final LanguageRepository languageRepository;
    private final RoomLanMapper roomLanMapper;

    @Override
    @Transactional
    public RoomLanDto create(CreateRoomLanDto dto) {
        Room room = roomRepository.findById(dto.getRoomId())
            .orElseThrow(() -> new NotFoundException("Room not found with id: " + dto.getRoomId()));
        
        Language language = languageRepository.findByCode(dto.getLangCode())
            .orElseThrow(() -> new NotFoundException("Language not found with code: " + dto.getLangCode()));
        
        roomLanRepository.findByRoomIdAndLanguageCode(dto.getRoomId(), dto.getLangCode())
            .ifPresent(existing -> {
                throw new InternalServerException(
                    "Translation already exists for room " + dto.getRoomId() + " and language " + dto.getLangCode()
                );
            });
        
        RoomLan roomLan = RoomLan.builder()
            .room(room)
            .language(language)
            .name(dto.getName())
            .description(dto.getDescription())
            .locationDescription(dto.getLocationDescription())
            .build();
        
        roomLan = roomLanRepository.save(roomLan);
        return roomLanMapper.toDto(roomLan);
    }

    @Override
    @Transactional
    public RoomLanDto update(Long id, UpdateRoomLanDto dto) {
        RoomLan roomLan = roomLanRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("RoomLan not found with id: " + id));
        
        if (dto.getName() != null) {
            roomLan.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            roomLan.setDescription(dto.getDescription());
        }
        if (dto.getLocationDescription() != null) {
            roomLan.setLocationDescription(dto.getLocationDescription());
        }
        
        roomLan = roomLanRepository.save(roomLan);
        return roomLanMapper.toDto(roomLan);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!roomLanRepository.existsById(id)) {
            throw new NotFoundException("RoomLan not found with id: " + id);
        }
        try {
            roomLanRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete RoomLan", ex);
        }
    }

    @Override
    public RoomLanDto getById(Long id) {
        RoomLan roomLan = roomLanRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("RoomLan not found with id: " + id));
        return roomLanMapper.toDto(roomLan);
    }

    @Override
    public List<RoomLanDto> getByRoomId(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new NotFoundException("Room not found with id: " + roomId);
        }
        
        List<RoomLan> roomLans = roomLanRepository.findByRoomId(roomId);
        return roomLans.stream()
            .map(roomLanMapper::toDto)
            .toList();
    }

    @Override
    public RoomLanDto getByRoomIdAndLangCode(Long roomId, String langCode) {
        RoomLan roomLan = roomLanRepository.findByRoomIdAndLanguageCode(roomId, langCode)
            .orElseThrow(() -> new NotFoundException(
                "Translation not found for room " + roomId + " and language " + langCode
            ));
        return roomLanMapper.toDto(roomLan);
    }

    @Override
    @Transactional
    public RoomLanDto upsertByRoomIdAndLangCode(Long roomId, String langCode, UpdateRoomLanDto dto) {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new NotFoundException("Room not found with id: " + roomId));
        
        Language language = languageRepository.findByCode(langCode)
            .orElseThrow(() -> new NotFoundException("Language not found with code: " + langCode));
        
        RoomLan roomLan = roomLanRepository.findByRoomIdAndLanguageCode(roomId, langCode)
            .orElse(null);
        
        if (roomLan != null) {
            if (dto.getName() != null) {
                roomLan.setName(dto.getName());
            }
            if (dto.getDescription() != null) {
                roomLan.setDescription(dto.getDescription());
            }
            if (dto.getLocationDescription() != null) {
                roomLan.setLocationDescription(dto.getLocationDescription());
            }
        } else {
            roomLan = RoomLan.builder()
                .room(room)
                .language(language)
                .name(dto.getName())
                .description(dto.getDescription())
                .locationDescription(dto.getLocationDescription())
                .build();
        }
        
        roomLan = roomLanRepository.save(roomLan);
        return roomLanMapper.toDto(roomLan);
    }

    @Override
    @Transactional
    public void deleteByRoomIdAndLangCode(Long roomId, String langCode) {
        RoomLan roomLan = roomLanRepository.findByRoomIdAndLanguageCode(roomId, langCode)
            .orElseThrow(() -> new NotFoundException(
                "Translation not found for room " + roomId + " and language " + langCode
            ));
        
        try {
            roomLanRepository.delete(roomLan);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete RoomLan", ex);
        }
    }
}
