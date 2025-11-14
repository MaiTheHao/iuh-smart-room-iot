package com.example.smartroom.modules.floor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.smartroom.core.common.vo.ApiResponseBody;
import com.example.smartroom.modules.floor.dto.FloorLanDto;
import com.example.smartroom.modules.floor.dto.UpdateFloorLanDto;
import com.example.smartroom.modules.floor.service.FloorLanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/translations")
@RequiredArgsConstructor
public class FloorLanControllerV1 {
    
    private final FloorLanService floorLanService;

    /**
     * Update hoặc tạo translation cho floor theo langCode (Primary endpoint)
     * 
     * PUT /api/v1/translations/floor/{floorId}/lang/{langCode}
     * {
     *   "name": "Tầng 1 - Cập nhật",
     *   "description": "Mô tả mới",
     *   "locationDescription": "Vị trí mới"
     * }
     */
    @PutMapping("/floor/{floorId}/lang/{langCode}")
    public ResponseEntity<ApiResponseBody<FloorLanDto>> upsertByFloorIdAndLangCode(
            @PathVariable Long floorId,
            @PathVariable String langCode,
            @RequestBody @Valid UpdateFloorLanDto dto) {
        FloorLanDto result = floorLanService.upsertByFloorIdAndLangCode(floorId, langCode, dto);
        return ResponseEntity.ok(ApiResponseBody.success("Translation updated successfully", result));
    }

    /**
     * Lấy tất cả translations của một floor
     * 
     * GET /api/v1/translations/floor/{floorId}
     */
    @GetMapping("/floor/{floorId}")
    public ResponseEntity<ApiResponseBody<List<FloorLanDto>>> getTranslationsByFloorId(
            @PathVariable Long floorId) {
        List<FloorLanDto> translations = floorLanService.getByFloorId(floorId);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", translations));
    }

    /**
     * Lấy translation của floor theo language code
     * 
     * GET /api/v1/translations/floor/{floorId}/lang/{langCode}
     */
    @GetMapping("/floor/{floorId}/lang/{langCode}")
    public ResponseEntity<ApiResponseBody<FloorLanDto>> getTranslationByLanguage(
            @PathVariable Long floorId,
            @PathVariable String langCode) {
        FloorLanDto translation = floorLanService.getByFloorIdAndLangCode(floorId, langCode);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", translation));
    }

    /**
     * Xóa một translation cụ thể của floor
     * 
     * DELETE /api/v1/translations/floor/{floorId}/lang/{langCode}
     */
    @DeleteMapping("/floor/{floorId}/lang/{langCode}")
    public ResponseEntity<ApiResponseBody<Boolean>> deleteTranslation(
            @PathVariable Long floorId,
            @PathVariable String langCode) {
        floorLanService.deleteByFloorIdAndLangCode(floorId, langCode);
        return ResponseEntity.ok(ApiResponseBody.success("Translation deleted successfully", true));
    }
}
