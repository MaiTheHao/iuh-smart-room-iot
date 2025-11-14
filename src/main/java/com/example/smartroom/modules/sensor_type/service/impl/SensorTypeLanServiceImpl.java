package com.example.smartroom.modules.sensor_type.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.smartroom.core.common.exception.InternalServerException;
import com.example.smartroom.core.common.exception.NotFoundException;
import com.example.smartroom.modules.sensor_type.entity.SensorType;
import com.example.smartroom.modules.sensor_type.entity.SensorTypeLan;
import com.example.smartroom.modules.sensor_type.dto.CreateSensorTypeLanDto;
import com.example.smartroom.modules.sensor_type.dto.SensorTypeLanDto;
import com.example.smartroom.modules.sensor_type.dto.UpdateSensorTypeLanDto;
import com.example.smartroom.modules.sensor_type.mapper.SensorTypeLanMapper;
import com.example.smartroom.modules.sensor_type.repository.SensorTypeLanRepository;
import com.example.smartroom.modules.sensor_type.repository.SensorTypeRepository;
import com.example.smartroom.modules.sensor_type.service.SensorTypeLanService;
import com.example.smartroom.modules.language.entity.Language;
import com.example.smartroom.modules.language.repository.LanguageRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SensorTypeLanServiceImpl implements SensorTypeLanService {
    
    private final SensorTypeLanRepository sensorTypeLanRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final LanguageRepository languageRepository;
    private final SensorTypeLanMapper sensorTypeLanMapper;

    @Override
    @Transactional
    public SensorTypeLanDto create(CreateSensorTypeLanDto dto) {
        // This method would need sensorTypeId and langCode from controller
        // For now, throwing exception as it requires external params
        throw new InternalServerException("Use upsertBySensorTypeIdAndLangCode instead");
    }

    @Override
    @Transactional
    public SensorTypeLanDto update(Long id, UpdateSensorTypeLanDto dto) {
        SensorTypeLan sensorTypeLan = sensorTypeLanRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("SensorTypeLan not found with id: " + id));
        
        if (dto.getName() != null) {
            sensorTypeLan.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            sensorTypeLan.setDescription(dto.getDescription());
        }
        
        sensorTypeLan = sensorTypeLanRepository.save(sensorTypeLan);
        return sensorTypeLanMapper.toDto(sensorTypeLan);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!sensorTypeLanRepository.existsById(id)) {
            throw new NotFoundException("SensorTypeLan not found with id: " + id);
        }
        try {
            sensorTypeLanRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete SensorTypeLan", ex);
        }
    }

    @Override
    public SensorTypeLanDto getById(Long id) {
        SensorTypeLan sensorTypeLan = sensorTypeLanRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("SensorTypeLan not found with id: " + id));
        return sensorTypeLanMapper.toDto(sensorTypeLan);
    }

    @Override
    public List<SensorTypeLanDto> getBySensorTypeId(Long sensorTypeId) {
        if (!sensorTypeRepository.existsById(sensorTypeId)) {
            throw new NotFoundException("SensorType not found with id: " + sensorTypeId);
        }
        
        List<SensorTypeLan> sensorTypeLans = sensorTypeLanRepository.findBySensorTypeId(sensorTypeId);
        return sensorTypeLans.stream()
            .map(sensorTypeLanMapper::toDto)
            .toList();
    }

    @Override
    public SensorTypeLanDto getBySensorTypeIdAndLangCode(Long sensorTypeId, String langCode) {
        SensorTypeLan sensorTypeLan = sensorTypeLanRepository.findBySensorTypeIdAndLanguageCode(sensorTypeId, langCode)
            .orElseThrow(() -> new NotFoundException(
                "Translation not found for sensor type " + sensorTypeId + " and language " + langCode
            ));
        return sensorTypeLanMapper.toDto(sensorTypeLan);
    }

    @Override
    @Transactional
    public SensorTypeLanDto upsertBySensorTypeIdAndLangCode(Long sensorTypeId, String langCode, UpdateSensorTypeLanDto dto) {
        SensorType sensorType = sensorTypeRepository.findById(sensorTypeId)
            .orElseThrow(() -> new NotFoundException("SensorType not found with id: " + sensorTypeId));
        
        Language language = languageRepository.findByCode(langCode)
            .orElseThrow(() -> new NotFoundException("Language not found with code: " + langCode));
        
        SensorTypeLan sensorTypeLan = sensorTypeLanRepository.findBySensorTypeIdAndLanguageCode(sensorTypeId, langCode)
            .orElse(null);
        
        if (sensorTypeLan != null) {
            if (dto.getName() != null) {
                sensorTypeLan.setName(dto.getName());
            }
            if (dto.getDescription() != null) {
                sensorTypeLan.setDescription(dto.getDescription());
            }
        } else {
            sensorTypeLan = SensorTypeLan.builder()
                .sensorType(sensorType)
                .language(language)
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        }
        
        sensorTypeLan = sensorTypeLanRepository.save(sensorTypeLan);
        return sensorTypeLanMapper.toDto(sensorTypeLan);
    }

    @Override
    @Transactional
    public void deleteBySensorTypeIdAndLangCode(Long sensorTypeId, String langCode) {
        SensorTypeLan sensorTypeLan = sensorTypeLanRepository.findBySensorTypeIdAndLanguageCode(sensorTypeId, langCode)
            .orElseThrow(() -> new NotFoundException(
                "Translation not found for sensor type " + sensorTypeId + " and language " + langCode
            ));
        
        try {
            sensorTypeLanRepository.delete(sensorTypeLan);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete SensorTypeLan", ex);
        }
    }
}
