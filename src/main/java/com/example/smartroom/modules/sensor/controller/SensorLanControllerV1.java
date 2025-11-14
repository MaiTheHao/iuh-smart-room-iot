package com.example.smartroom.modules.sensor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smartroom.core.common.vo.ApiResponse;
import com.example.smartroom.modules.sensor.dto.SensorLanDto;
import com.example.smartroom.modules.sensor.dto.UpdateSensorLanDto;
import com.example.smartroom.modules.sensor.service.SensorLanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST API Controller for Sensor translation management
 * 
 * Endpoints:
 * - GET /api/v1/translations/sensor/{id} - Get all translations for a sensor
 * - GET /api/v1/translations/sensor/{id}/lang/{code} - Get specific language translation
 * - PUT /api/v1/translations/sensor/{id}/lang/{code} - Upsert translation
 * - DELETE /api/v1/translations/sensor/{id}/lang/{code} - Delete translation
 */
@RestController
@RequestMapping("/api/v1/translations/sensor")
@Validated
@RequiredArgsConstructor
public class SensorLanControllerV1 {
    
    private final SensorLanService sensorLanService;
    
    /**
     * Get all translations for a sensor
     * 
     * @param sensorId The sensor ID
     * @return List of translations for the sensor
     */
    @GetMapping("/{sensorId}")
    public ResponseEntity<ApiResponse<List<SensorLanDto>>> getBySensorId(@PathVariable Long sensorId) {
        List<SensorLanDto> result = sensorLanService.getBySensorId(sensorId);
        return ResponseEntity.ok(ApiResponse.success("Translations retrieved successfully", result));
    }
    
    /**
     * Get specific language translation for a sensor
     * 
     * @param sensorId The sensor ID
     * @param code The language code
     * @return The translation for the specified language
     */
    @GetMapping("/{sensorId}/lang/{code}")
    public ResponseEntity<ApiResponse<SensorLanDto>> getBySensorIdAndLangCode(
        @PathVariable Long sensorId,
        @PathVariable String code
    ) {
        SensorLanDto result = sensorLanService.getBySensorIdAndLangCode(sensorId, code);
        return ResponseEntity.ok(ApiResponse.success("Translation retrieved successfully", result));
    }
    
    /**
     * Upsert (create or update) translation for a sensor in a specific language
     * 
     * @param sensorId The sensor ID
     * @param code The language code
     * @param dto The translation update DTO
     * @return The created or updated translation
     */
    @PutMapping("/{sensorId}/lang/{code}")
    public ResponseEntity<ApiResponse<SensorLanDto>> upsertBySensorIdAndLangCode(
        @PathVariable Long sensorId,
        @PathVariable String code,
        @Valid @RequestBody UpdateSensorLanDto dto
    ) {
        SensorLanDto result = sensorLanService.upsertBySensorIdAndLangCode(sensorId, code, dto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success("Translation saved successfully", result));
    }
    
    /**
     * Delete translation for a sensor in a specific language
     * 
     * @param sensorId The sensor ID
     * @param code The language code
     * @return Success message
     */
    @DeleteMapping("/{sensorId}/lang/{code}")
    public ResponseEntity<ApiResponse<Void>> deleteBySensorIdAndLangCode(
        @PathVariable Long sensorId,
        @PathVariable String code
    ) {
        sensorLanService.deleteBySensorIdAndLangCode(sensorId, code);
        return ResponseEntity.ok(ApiResponse.success("Translation deleted successfully"));
    }
}
