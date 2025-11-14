package com.example.smartroom.modules.language.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.smartroom.core.common.exception.InternalServerException;
import com.example.smartroom.core.common.exception.NotFoundException;
import com.example.smartroom.modules.language.dto.CreateLanguageDto;
import com.example.smartroom.modules.language.dto.LanguageDto;
import com.example.smartroom.modules.language.dto.UpdateLanguageDto;
import com.example.smartroom.modules.language.entity.Language;
import com.example.smartroom.modules.language.mapper.LanguageMapper;
import com.example.smartroom.modules.language.repository.LanguageRepository;
import com.example.smartroom.modules.language.service.LanguageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {
    
    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    @Override
    @Transactional
    public LanguageDto create(CreateLanguageDto dto) {
        // Check if code already exists
        languageRepository.findByCode(dto.getCode())
            .ifPresent(existing -> {
                throw new InternalServerException("Language code already exists: " + dto.getCode());
            });
        
        Language language = languageMapper.toEntity(dto);
        language = languageRepository.save(language);
        return languageMapper.toDto(language);
    }

    @Override
    @Transactional
    public LanguageDto update(Long id, UpdateLanguageDto dto) {
        Language language = languageRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Language not found with id: " + id));
        
        languageMapper.updateEntityFromDto(language, dto);
        language = languageRepository.save(language);
        return languageMapper.toDto(language);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!languageRepository.existsById(id)) {
            throw new NotFoundException("Language not found with id: " + id);
        }
        try {
            languageRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete language. It might be in use by translations.", ex);
        }
    }

    @Override
    public LanguageDto getById(Long id) {
        Language language = languageRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Language not found with id: " + id));
        return languageMapper.toDto(language);
    }

    @Override
    public LanguageDto getByCode(String code) {
        Language language = languageRepository.findByCode(code)
            .orElseThrow(() -> new NotFoundException("Language not found with code: " + code));
        return languageMapper.toDto(language);
    }

    @Override
    public List<LanguageDto> getAll() {
        List<Language> languages = languageRepository.findAll();
        return languages.stream()
            .map(languageMapper::toDto)
            .toList();
    }
}
