package com.example.smartroom.modules.sensor_type.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.smartroom.core.common.vo.ApiResponseBody;
import com.example.smartroom.modules.sensor_type.dto.SensorTypeLanDto;
import com.example.smartroom.modules.sensor_type.dto.UpdateSensorTypeLanDto;
import com.example.smartroom.modules.sensor_type.service.SensorTypeLanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/translations")
@RequiredArgsConstructor
public class SensorTypeLanControllerV1 {
    
    private final SensorTypeLanService sensorTypeLanService;

    /**
     * Update hoặc tạo translation cho sensor type theo langCode (Primary endpoint)
     * 
     * PUT /api/v1/translations/sensor-type/{sensorTypeId}/lang/{langCode}
     * {
     *   "name": "Cảm biến nhiệt độ",
     *   "description": "Đo nhiệt độ môi trường"
     * }
     */
    @PutMapping("/sensor-type/{sensorTypeId}/lang/{langCode}")
    public ResponseEntity<ApiResponseBody<SensorTypeLanDto>> upsertBySensorTypeIdAndLangCode(
            @PathVariable Long sensorTypeId,
            @PathVariable String langCode,
            @RequestBody @Valid UpdateSensorTypeLanDto dto) {
        SensorTypeLanDto result = sensorTypeLanService.upsertBySensorTypeIdAndLangCode(sensorTypeId, langCode, dto);
        return ResponseEntity.ok(ApiResponseBody.success("Translation updated successfully", result));
    }

    /**
     * Lấy tất cả translations của một sensor type
     * 
     * GET /api/v1/translations/sensor-type/{sensorTypeId}
     */
    @GetMapping("/sensor-type/{sensorTypeId}")
    public ResponseEntity<ApiResponseBody<List<SensorTypeLanDto>>> getTranslationsBySensorTypeId(
            @PathVariable Long sensorTypeId) {
        List<SensorTypeLanDto> translations = sensorTypeLanService.getBySensorTypeId(sensorTypeId);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", translations));
    }

    /**
     * Lấy translation của sensor type theo language code
     * 
     * GET /api/v1/translations/sensor-type/{sensorTypeId}/lang/{langCode}
     */
    @GetMapping("/sensor-type/{sensorTypeId}/lang/{langCode}")
    public ResponseEntity<ApiResponseBody<SensorTypeLanDto>> getTranslationByLanguage(
            @PathVariable Long sensorTypeId,
            @PathVariable String langCode) {
        SensorTypeLanDto translation = sensorTypeLanService.getBySensorTypeIdAndLangCode(sensorTypeId, langCode);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", translation));
    }

    /**
     * Xóa một translation cụ thể của sensor type
     * 
     * DELETE /api/v1/translations/sensor-type/{sensorTypeId}/lang/{langCode}
     */
    @DeleteMapping("/sensor-type/{sensorTypeId}/lang/{langCode}")
    public ResponseEntity<ApiResponseBody<Boolean>> deleteTranslation(
            @PathVariable Long sensorTypeId,
            @PathVariable String langCode) {
        sensorTypeLanService.deleteBySensorTypeIdAndLangCode(sensorTypeId, langCode);
        return ResponseEntity.ok(ApiResponseBody.success("Translation deleted successfully", true));
    }
}
