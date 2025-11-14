package com.example.smartroom.modules.language.service;

import java.util.List;

import com.example.smartroom.modules.language.dto.CreateLanguageDto;
import com.example.smartroom.modules.language.dto.LanguageDto;
import com.example.smartroom.modules.language.dto.UpdateLanguageDto;

/**
 * Service để quản lý danh sách Language (ngôn ngữ hệ thống)
 */
public interface LanguageService {
    /**
     * Tạo language mới
     */
    LanguageDto create(CreateLanguageDto dto);
    
    /**
     * Update language
     */
    LanguageDto update(Long id, UpdateLanguageDto dto);
    
    /**
     * Xóa language
     */
    void delete(Long id);
    
    /**
     * Lấy language theo ID
     */
    LanguageDto getById(Long id);
    
    /**
     * Lấy language theo code
     */
    LanguageDto getByCode(String code);
    
    /**
     * Lấy tất cả languages
     */
    List<LanguageDto> getAll();
}
