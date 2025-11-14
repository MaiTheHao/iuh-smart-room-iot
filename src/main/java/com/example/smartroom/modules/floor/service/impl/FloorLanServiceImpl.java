package com.example.smartroom.modules.floor.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.smartroom.core.common.exception.InternalServerException;
import com.example.smartroom.core.common.exception.NotFoundException;
import com.example.smartroom.modules.floor.dto.CreateFloorLanDto;
import com.example.smartroom.modules.floor.dto.FloorLanDto;
import com.example.smartroom.modules.floor.dto.UpdateFloorLanDto;
import com.example.smartroom.modules.floor.entity.Floor;
import com.example.smartroom.modules.floor.entity.FloorLan;
import com.example.smartroom.modules.floor.mapper.FloorLanMapper;
import com.example.smartroom.modules.floor.repository.FloorLanRepository;
import com.example.smartroom.modules.floor.repository.FloorRepository;
import com.example.smartroom.modules.floor.service.FloorLanService;
import com.example.smartroom.modules.language.entity.Language;
import com.example.smartroom.modules.language.repository.LanguageRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FloorLanServiceImpl implements FloorLanService {
    
    private final FloorLanRepository floorLanRepository;
    private final FloorRepository floorRepository;
    private final LanguageRepository languageRepository;
    private final FloorLanMapper floorLanMapper;

    @Override
    @Transactional
    public FloorLanDto create(CreateFloorLanDto dto) {
        Floor floor = floorRepository.findById(dto.getFloorId())
            .orElseThrow(() -> new NotFoundException("Floor not found with id: " + dto.getFloorId()));
        
        Language language = languageRepository.findByCode(dto.getLangCode())
            .orElseThrow(() -> new NotFoundException("Language not found with code: " + dto.getLangCode()));
        
        floorLanRepository.findByFloorIdAndLanguageCode(dto.getFloorId(), dto.getLangCode())
            .ifPresent(existing -> {
                throw new InternalServerException(
                    "Translation already exists for floor " + dto.getFloorId() + " and language " + dto.getLangCode()
                );
            });

        FloorLan floorLan = FloorLan.builder()
            .floor(floor)
            .language(language)
            .name(dto.getName())
            .description(dto.getDescription())
            .locationDescription(dto.getLocationDescription())
            .build();
        
        floorLan = floorLanRepository.save(floorLan);
        return floorLanMapper.toDto(floorLan);
    }

    @Override
    @Transactional
    public FloorLanDto update(Long id, UpdateFloorLanDto dto) {
        FloorLan floorLan = floorLanRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("FloorLan not found with id: " + id));
        
        if (dto.getName() != null) {
            floorLan.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            floorLan.setDescription(dto.getDescription());
        }
        if (dto.getLocationDescription() != null) {
            floorLan.setLocationDescription(dto.getLocationDescription());
        }
        
        floorLan = floorLanRepository.save(floorLan);
        return floorLanMapper.toDto(floorLan);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!floorLanRepository.existsById(id)) {
            throw new NotFoundException("FloorLan not found with id: " + id);
        }
        try {
            floorLanRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete FloorLan", ex);
        }
    }

    @Override
    public FloorLanDto getById(Long id) {
        FloorLan floorLan = floorLanRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("FloorLan not found with id: " + id));
        return floorLanMapper.toDto(floorLan);
    }

    @Override
    public List<FloorLanDto> getByFloorId(Long floorId) {
        if (!floorRepository.existsById(floorId)) {
            throw new NotFoundException("Floor not found with id: " + floorId);
        }
        
        List<FloorLan> floorLans = floorLanRepository.findByFloorId(floorId);
        return floorLans.stream()
            .map(floorLanMapper::toDto)
            .toList();
    }

    @Override
    public FloorLanDto getByFloorIdAndLangCode(Long floorId, String langCode) {
        FloorLan floorLan = floorLanRepository.findByFloorIdAndLanguageCode(floorId, langCode)
            .orElseThrow(() -> new NotFoundException(
                "Translation not found for floor " + floorId + " and language " + langCode
            ));
        return floorLanMapper.toDto(floorLan);
    }

    @Override
    @Transactional
    public FloorLanDto upsertByFloorIdAndLangCode(Long floorId, String langCode, UpdateFloorLanDto dto) {
        Floor floor = floorRepository.findById(floorId)
            .orElseThrow(() -> new NotFoundException("Floor not found with id: " + floorId));
        
        Language language = languageRepository.findByCode(langCode)
            .orElseThrow(() -> new NotFoundException("Language not found with code: " + langCode));
        
        FloorLan floorLan = floorLanRepository.findByFloorIdAndLanguageCode(floorId, langCode)
            .orElse(null);
        
        if (floorLan != null) {
            if (dto.getName() != null) {
                floorLan.setName(dto.getName());
            }
            if (dto.getDescription() != null) {
                floorLan.setDescription(dto.getDescription());
            }
            if (dto.getLocationDescription() != null) {
                floorLan.setLocationDescription(dto.getLocationDescription());
            }
        } else {
            floorLan = FloorLan.builder()
                .floor(floor)
                .language(language)
                .name(dto.getName())
                .description(dto.getDescription())
                .locationDescription(dto.getLocationDescription())
                .build();
        }
        
        floorLan = floorLanRepository.save(floorLan);
        return floorLanMapper.toDto(floorLan);
    }

    @Override
    @Transactional
    public void deleteByFloorIdAndLangCode(Long floorId, String langCode) {
        FloorLan floorLan = floorLanRepository.findByFloorIdAndLanguageCode(floorId, langCode)
            .orElseThrow(() -> new NotFoundException(
                "Translation not found for floor " + floorId + " and language " + langCode
            ));
        
        try {
            floorLanRepository.delete(floorLan);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete FloorLan", ex);
        }
    }
}
