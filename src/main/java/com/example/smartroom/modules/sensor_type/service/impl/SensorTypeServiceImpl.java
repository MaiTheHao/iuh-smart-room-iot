package com.example.smartroom.modules.sensor_type.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.smartroom.core.common.exception.BadRequestException;
import com.example.smartroom.core.common.exception.InternalServerException;
import com.example.smartroom.core.common.exception.NotFoundException;
import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.sensor_type.entity.SensorType;
import com.example.smartroom.modules.sensor_type.entity.SensorTypeLan;
import com.example.smartroom.modules.sensor_type.dto.CreateSensorTypeDto;
import com.example.smartroom.modules.sensor_type.dto.SensorTypeDto;
import com.example.smartroom.modules.sensor_type.dto.UpdateSensorTypeDto;
import com.example.smartroom.modules.sensor_type.mapper.SensorTypeMapper;
import com.example.smartroom.modules.sensor_type.repository.SensorTypeRepository;
import com.example.smartroom.modules.sensor_type.service.SensorTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SensorTypeServiceImpl implements SensorTypeService {
    private final SensorTypeRepository sensorTypeRepository;
    private final SensorTypeMapper sensorTypeMapper;

    @Override
    @Transactional
    public SensorTypeDto create(CreateSensorTypeDto dto) {
        sensorTypeRepository.findByCode(dto.getCode())
            .ifPresent(existing -> {
                throw new BadRequestException(
                    "SensorType with code '" + dto.getCode() + "' already exists"
                );
            });
        
        SensorType sensorType = sensorTypeMapper.toEntity(dto);
        sensorType = sensorTypeRepository.save(sensorType);
        
        SensorTypeDto result = sensorTypeMapper.toDto(sensorType);
        return result;
    }

    @Override
    @Transactional
    public SensorTypeDto update(Long id, UpdateSensorTypeDto dto, String langCode) {
        SensorType sensorType = sensorTypeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("SensorType not found with id: " + id));
        
        if (dto.getCode() != null) {
            sensorType.setCode(dto.getCode());
        }
        
        sensorType = sensorTypeRepository.save(sensorType);
        SensorTypeDto result = sensorTypeMapper.toDto(sensorType);
        
        if (langCode != null) {
            populateTranslationFields(result, sensorType, langCode);
        }
        
        return result;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!sensorTypeRepository.existsById(id)) {
            throw new NotFoundException("SensorType not found with id: " + id);
        }
        try {
            sensorTypeRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete sensor type", ex);
        }
    }

    @Override
    public SensorTypeDto getById(Long id, String langCode) {
        SensorType sensorType = sensorTypeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("SensorType not found with id: " + id));
        
        SensorTypeDto result = sensorTypeMapper.toDto(sensorType);
        
        if (langCode != null) {
            populateTranslationFields(result, sensorType, langCode);
        }
        
        return result;
    }

    @Override
    public SensorTypeDto getByCode(String code, String langCode) {
        SensorType sensorType = sensorTypeRepository.findByCode(code)
            .orElseThrow(() -> new NotFoundException("SensorType not found with code: " + code));
        
        SensorTypeDto result = sensorTypeMapper.toDto(sensorType);
        
        if (langCode != null) {
            populateTranslationFields(result, sensorType, langCode);
        }
        
        return result;
    }

    @Override
    public PaginationInfo<SensorTypeDto> getList(Pageable pageable, String langCode) {
        Page<SensorType> sensorTypePage = sensorTypeRepository.findAll(pageable);
        
        List<SensorTypeDto> dtoList = sensorTypePage.getContent().stream()
            .map(sensorType -> {
                SensorTypeDto dto = sensorTypeMapper.toDto(sensorType);
                
                if (langCode != null) {
                    populateTranslationFields(dto, sensorType, langCode);
                }
                
                return dto;
            })
            .toList();
        
        return PaginationInfo.from(sensorTypePage, dtoList);
    }

    private void populateTranslationFields(SensorTypeDto dto, SensorType sensorType, String langCode) {
        SensorTypeLan matchedLan = sensorType.getSensorTypeLans().stream()
            .filter(lan -> lan.getLanguage().getCode().equalsIgnoreCase(langCode))
            .findFirst()
            .orElse(null);
        
        if (matchedLan != null) {
            dto.setName(matchedLan.getName());
            dto.setDescription(matchedLan.getDescription());
            dto.setLangCode(matchedLan.getLanguage().getCode());
        }
    }
}
