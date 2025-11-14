package com.example.smartroom.modules.device.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.smartroom.core.common.vo.ApiResponseBody;
import com.example.smartroom.modules.device.dto.DeviceLanDto;
import com.example.smartroom.modules.device.dto.UpdateDeviceLanDto;
import com.example.smartroom.modules.device.service.DeviceLanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/translations")
@RequiredArgsConstructor
public class DeviceLanControllerV1 {

    private final DeviceLanService deviceLanService;

    /**
     * Update hoặc tạo translation cho device theo langCode (Primary endpoint)
     * 
     * PUT /api/v1/translations/device/{deviceId}/lang/{langCode}
     * {
     *   "name": "...",
     *   "description": "...",
     *   "locationDescription": "..."
     * }
     */
    @PutMapping("/device/{deviceId}/lang/{langCode}")
    public ResponseEntity<ApiResponseBody<DeviceLanDto>> upsertByDeviceIdAndLangCode(
            @PathVariable Long deviceId,
            @PathVariable String langCode,
            @RequestBody @Valid UpdateDeviceLanDto dto) {
        DeviceLanDto result = deviceLanService.upsertByDeviceIdAndLangCode(deviceId, langCode, dto);
        return ResponseEntity.ok(ApiResponseBody.success("Translation updated successfully", result));
    }

    /**
     * Lấy tất cả translations của một device
     * 
     * GET /api/v1/translations/device/{deviceId}
     */
    @GetMapping("/device/{deviceId}")
    public ResponseEntity<ApiResponseBody<List<DeviceLanDto>>> getTranslationsByDeviceId(
            @PathVariable Long deviceId) {
        List<DeviceLanDto> translations = deviceLanService.getByDeviceId(deviceId);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", translations));
    }

    /**
     * Lấy translation của device theo language code
     * 
     * GET /api/v1/translations/device/{deviceId}/lang/{langCode}
     */
    @GetMapping("/device/{deviceId}/lang/{langCode}")
    public ResponseEntity<ApiResponseBody<DeviceLanDto>> getTranslationByLanguage(
            @PathVariable Long deviceId,
            @PathVariable String langCode) {
        DeviceLanDto translation = deviceLanService.getByDeviceIdAndLangCode(deviceId, langCode);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", translation));
    }

    /**
     * Xóa một translation cụ thể của device
     * 
     * DELETE /api/v1/translations/device/{deviceId}/lang/{langCode}
     */
    @DeleteMapping("/device/{deviceId}/lang/{langCode}")
    public ResponseEntity<ApiResponseBody<Boolean>> deleteTranslation(
            @PathVariable Long deviceId,
            @PathVariable String langCode) {
        deviceLanService.deleteByDeviceIdAndLangCode(deviceId, langCode);
        return ResponseEntity.ok(ApiResponseBody.success("Translation deleted successfully", true));
    }
}
