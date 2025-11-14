package com.example.smartroom.modules.sensor_type.controller;

import com.example.smartroom.core.common.vo.ApiResponseBody;
import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.sensor_type.dto.CreateSensorTypeDto;
import com.example.smartroom.modules.sensor_type.dto.SensorTypeDto;
import com.example.smartroom.modules.sensor_type.dto.UpdateSensorTypeDto;
import com.example.smartroom.modules.sensor_type.service.SensorTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensor-types")
@RequiredArgsConstructor
public class SensorTypeControllerV1 {
    private final SensorTypeService sensorTypeService;

    /**
     * Tạo sensor type mới - CHỈ có code
     * Translations tạo riêng qua PUT /api/v1/translations
     * 
     * Request body example:
     * {
     *   "code": "TEMPERATURE"
     * }
     */
    @PostMapping
    public ResponseEntity<ApiResponseBody<SensorTypeDto>> createSensorType(
            @RequestBody @Valid CreateSensorTypeDto createSensorTypeDto) {
        SensorTypeDto sensorTypeDto = sensorTypeService.create(createSensorTypeDto);
        return ResponseEntity.ok(ApiResponseBody.success("Created successfully", sensorTypeDto));
    }

    /**
     * Lấy danh sách sensor types với pagination và optional language filter
     * 
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @param lang Language code (optional) - flatten translation fields vào response
     * 
     * Example: GET /api/v1/sensor-types?page=0&size=10&lang=vi
     * Response: {id, code, name, description, langCode}
     */
    @GetMapping
    public ResponseEntity<ApiResponseBody<PaginationInfo<SensorTypeDto>>> getSensorTypes(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String lang) {
        PaginationInfo<SensorTypeDto> sensorTypePage = sensorTypeService.getList(pageable, lang);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", sensorTypePage));
    }

    /**
     * Lấy sensor type theo ID với optional language filter
     * 
     * @param id SensorType ID
     * @param lang Language code (optional) - flatten translation fields
     * 
     * Example: GET /api/v1/sensor-types/1?lang=en
     * Response: {id, code, name, description, langCode}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseBody<SensorTypeDto>> getSensorTypeById(
            @PathVariable Long id,
            @RequestParam(required = false) String lang) {
        SensorTypeDto sensorTypeDto = sensorTypeService.getById(id, lang);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", sensorTypeDto));
    }

    /**
     * Lấy sensor type theo code với optional language filter
     * 
     * @param code SensorType code
     * @param lang Language code (optional) - flatten translation fields
     * 
     * Example: GET /api/v1/sensor-types/code/TEMPERATURE?lang=vi
     * Response: {id, code, name, description, langCode}
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponseBody<SensorTypeDto>> getSensorTypeByCode(
            @PathVariable String code,
            @RequestParam(required = false) String lang) {
        SensorTypeDto sensorTypeDto = sensorTypeService.getByCode(code, lang);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", sensorTypeDto));
    }

    /**
     * Update sensor type - CHỈ update code
     * Translations update riêng qua PUT /api/v1/translations/:id
     * 
     * @param id SensorType ID
     * @param lang Language code (optional) - để flatten translation trong response
     * 
     * Request body example:
     * {
     *   "code": "HUMIDITY"
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseBody<SensorTypeDto>> updateSensorType(
            @PathVariable Long id,
            @RequestBody @Valid UpdateSensorTypeDto updateSensorTypeDto,
            @RequestParam(required = false) String lang) {
        SensorTypeDto sensorTypeDto = sensorTypeService.update(id, updateSensorTypeDto, lang);
        return ResponseEntity.ok(ApiResponseBody.success("Updated successfully", sensorTypeDto));
    }

    /**
     * Xóa sensor type (cascade delete tất cả translations)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseBody<Boolean>> deleteSensorType(@PathVariable Long id) {
        sensorTypeService.delete(id);
        return ResponseEntity.ok(ApiResponseBody.success("Deleted successfully", true));
    }
}
