package com.example.smartroom.modules.sensor.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.smartroom.core.common.exception.InternalServerException;
import com.example.smartroom.core.common.exception.NotFoundException;
import com.example.smartroom.modules.sensor.entity.Sensor;
import com.example.smartroom.modules.sensor.entity.SensorLan;
import com.example.smartroom.modules.sensor.dto.CreateSensorLanDto;
import com.example.smartroom.modules.sensor.dto.SensorLanDto;
import com.example.smartroom.modules.sensor.dto.UpdateSensorLanDto;
import com.example.smartroom.modules.sensor.mapper.SensorLanMapper;
import com.example.smartroom.modules.sensor.repository.SensorLanRepository;
import com.example.smartroom.modules.sensor.repository.SensorRepository;
import com.example.smartroom.modules.sensor.service.SensorLanService;
import com.example.smartroom.modules.language.entity.Language;
import com.example.smartroom.modules.language.repository.LanguageRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SensorLanServiceImpl implements SensorLanService {
    
    private final SensorLanRepository sensorLanRepository;
    private final SensorRepository sensorRepository;
    private final LanguageRepository languageRepository;
    private final SensorLanMapper sensorLanMapper;

    @Override
    @Transactional
    public SensorLanDto create(CreateSensorLanDto dto) {
        throw new InternalServerException("Use upsertBySensorIdAndLangCode instead");
    }

    @Override
    @Transactional
    public SensorLanDto update(Long id, UpdateSensorLanDto dto) {
        SensorLan sensorLan = sensorLanRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("SensorLan not found with id: " + id));
        
        if (dto.getName() != null) {
            sensorLan.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            sensorLan.setDescription(dto.getDescription());
        }
        if (dto.getLocationDescription() != null) {
            sensorLan.setLocationDescription(dto.getLocationDescription());
        }
        
        sensorLan = sensorLanRepository.save(sensorLan);
        return sensorLanMapper.toDto(sensorLan);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!sensorLanRepository.existsById(id)) {
            throw new NotFoundException("SensorLan not found with id: " + id);
        }
        try {
            sensorLanRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete SensorLan", ex);
        }
    }

    @Override
    public SensorLanDto getById(Long id) {
        SensorLan sensorLan = sensorLanRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("SensorLan not found with id: " + id));
        return sensorLanMapper.toDto(sensorLan);
    }

    @Override
    public List<SensorLanDto> getBySensorId(Long sensorId) {
        if (!sensorRepository.existsById(sensorId)) {
            throw new NotFoundException("Sensor not found with id: " + sensorId);
        }
        
        List<SensorLan> sensorLans = sensorLanRepository.findBySensorId(sensorId);
        return sensorLans.stream()
            .map(sensorLanMapper::toDto)
            .toList();
    }

    @Override
    public SensorLanDto getBySensorIdAndLangCode(Long sensorId, String langCode) {
        SensorLan sensorLan = sensorLanRepository.findBySensorIdAndLanguageCode(sensorId, langCode)
            .orElseThrow(() -> new NotFoundException(
                "Translation not found for sensor " + sensorId + " and language " + langCode
            ));
        return sensorLanMapper.toDto(sensorLan);
    }

    @Override
    @Transactional
    public SensorLanDto upsertBySensorIdAndLangCode(Long sensorId, String langCode, UpdateSensorLanDto dto) {
        Sensor sensor = sensorRepository.findById(sensorId)
            .orElseThrow(() -> new NotFoundException("Sensor not found with id: " + sensorId));
        
        Language language = languageRepository.findByCode(langCode)
            .orElseThrow(() -> new NotFoundException("Language not found with code: " + langCode));
        
        SensorLan sensorLan = sensorLanRepository.findBySensorIdAndLanguageCode(sensorId, langCode)
            .orElse(null);
        
        if (sensorLan != null) {
            if (dto.getName() != null) {
                sensorLan.setName(dto.getName());
            }
            if (dto.getDescription() != null) {
                sensorLan.setDescription(dto.getDescription());
            }
            if (dto.getLocationDescription() != null) {
                sensorLan.setLocationDescription(dto.getLocationDescription());
            }
        } else {
            sensorLan = SensorLan.builder()
                .sensor(sensor)
                .language(language)
                .name(dto.getName())
                .description(dto.getDescription())
                .locationDescription(dto.getLocationDescription())
                .build();
        }
        
        sensorLan = sensorLanRepository.save(sensorLan);
        return sensorLanMapper.toDto(sensorLan);
    }

    @Override
    @Transactional
    public void deleteBySensorIdAndLangCode(Long sensorId, String langCode) {
        SensorLan sensorLan = sensorLanRepository.findBySensorIdAndLanguageCode(sensorId, langCode)
            .orElseThrow(() -> new NotFoundException(
                "Translation not found for sensor " + sensorId + " and language " + langCode
            ));
        
        try {
            sensorLanRepository.delete(sensorLan);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete SensorLan", ex);
        }
    }
}
