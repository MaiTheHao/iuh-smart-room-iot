package com.example.smartroom.modules.language.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.smartroom.core.common.vo.ApiResponseBody;
import com.example.smartroom.modules.language.dto.CreateLanguageDto;
import com.example.smartroom.modules.language.dto.LanguageDto;
import com.example.smartroom.modules.language.dto.UpdateLanguageDto;
import com.example.smartroom.modules.language.service.LanguageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/translations")
@RequiredArgsConstructor
public class LanguageControllerV1 {
    
    private final LanguageService languageService;

    /**
     * Tạo language mới
     * 
     * POST /api/v1/translations
     * {
     *   "code": "vi",
     *   "name": "Tiếng Việt"
     * }
     */
    @PostMapping
    public ResponseEntity<ApiResponseBody<LanguageDto>> createLanguage(
            @RequestBody @Valid CreateLanguageDto dto) {
        LanguageDto result = languageService.create(dto);
        return ResponseEntity.ok(ApiResponseBody.success("Language created successfully", result));
    }

    /**
     * Lấy tất cả translations
     * 
     * GET /api/v1/translations
     */
    @GetMapping
    public ResponseEntity<ApiResponseBody<List<LanguageDto>>> getAllLanguages() {
        List<LanguageDto> translations = languageService.getAll();
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", translations));
    }

    /**
     * Lấy language theo ID
     * 
     * GET /api/v1/translations/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseBody<LanguageDto>> getLanguageById(
            @PathVariable Long id) {
        LanguageDto language = languageService.getById(id);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", language));
    }

    /**
     * Lấy language theo code
     * 
     * GET /api/v1/translations/code/vi
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponseBody<LanguageDto>> getLanguageByCode(
            @PathVariable String code) {
        LanguageDto language = languageService.getByCode(code);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", language));
    }

    /**
     * Update language
     * 
     * PUT /api/v1/translations/1
     * {
     *   "name": "Tiếng Việt (Updated)"
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseBody<LanguageDto>> updateLanguage(
            @PathVariable Long id,
            @RequestBody @Valid UpdateLanguageDto dto) {
        LanguageDto result = languageService.update(id, dto);
        return ResponseEntity.ok(ApiResponseBody.success("Language updated successfully", result));
    }

    /**
     * Xóa language
     * 
     * DELETE /api/v1/translations/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseBody<Boolean>> deleteLanguage(
            @PathVariable Long id) {
        languageService.delete(id);
        return ResponseEntity.ok(ApiResponseBody.success("Language deleted successfully", true));
    }
}
