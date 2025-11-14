package com.example.smartroom.modules.sensor.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.smartroom.core.common.vo.ApiResponse;
import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.sensor.dto.CreateSensorDto;
import com.example.smartroom.modules.sensor.dto.SensorDto;
import com.example.smartroom.modules.sensor.dto.UpdateSensorDto;
import com.example.smartroom.modules.sensor.service.SensorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST API Controller for Sensor management
 * 
 * Endpoints:
 * - POST /api/v1/sensors - Create a new sensor
 * - GET /api/v1/sensors - Get all sensors with pagination
 * - GET /api/v1/sensors/{id} - Get sensor by ID
 * - GET /api/v1/sensors/device/{deviceId} - Get sensors by device
 * - GET /api/v1/sensors/sensor-type/{sensorTypeId} - Get sensors by sensor type
 * - PUT /api/v1/sensors/{id} - Update sensor
 * - DELETE /api/v1/sensors/{id} - Delete sensor
 */
@RestController
@RequestMapping("/api/v1/sensors")
@Validated
@RequiredArgsConstructor
public class SensorControllerV1 {
    
    private final SensorService sensorService;
    
    /**
     * Create a new sensor
     * 
     * @param dto The sensor creation DTO
     * @return Created sensor with generated ID
     */
    @PostMapping
    public ResponseEntity<ApiResponse<SensorDto>> create(@Valid @RequestBody CreateSensorDto dto) {
        SensorDto result = sensorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Sensor created successfully", result));
    }
    
    /**
     * Get all sensors with pagination
     * 
     * @param pageable Pagination information (page, size, sort)
     * @param langCode Optional language code for translations
     * @return Paginated list of sensors
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PaginationInfo<SensorDto>>> getList(
        Pageable pageable,
        @RequestParam(required = false) String langCode
    ) {
        PaginationInfo<SensorDto> result = sensorService.getList(pageable, langCode);
        return ResponseEntity.ok(ApiResponse.success("Sensors retrieved successfully", result));
    }
    
    /**
     * Get sensor by ID
     * 
     * @param id The sensor ID
     * @param langCode Optional language code for translations
     * @return The sensor details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SensorDto>> getById(
        @PathVariable Long id,
        @RequestParam(required = false) String langCode
    ) {
        SensorDto result = sensorService.getById(id, langCode);
        return ResponseEntity.ok(ApiResponse.success("Sensor retrieved successfully", result));
    }
    
    /**
     * Get sensors by device ID
     * 
     * @param deviceId The device ID
     * @param pageable Pagination information
     * @param langCode Optional language code for translations
     * @return Paginated list of sensors for the device
     */
    @GetMapping("/device/{deviceId}")
    public ResponseEntity<ApiResponse<PaginationInfo<SensorDto>>> getByDeviceId(
        @PathVariable Long deviceId,
        Pageable pageable,
        @RequestParam(required = false) String langCode
    ) {
        PaginationInfo<SensorDto> result = sensorService.getByDeviceId(deviceId, pageable, langCode);
        return ResponseEntity.ok(ApiResponse.success("Device sensors retrieved successfully", result));
    }
    
    /**
     * Get sensors by sensor type ID
     * 
     * @param sensorTypeId The sensor type ID
     * @param pageable Pagination information
     * @param langCode Optional language code for translations
     * @return Paginated list of sensors of the type
     */
    @GetMapping("/sensor-type/{sensorTypeId}")
    public ResponseEntity<ApiResponse<PaginationInfo<SensorDto>>> getBySensorTypeId(
        @PathVariable Long sensorTypeId,
        Pageable pageable,
        @RequestParam(required = false) String langCode
    ) {
        PaginationInfo<SensorDto> result = sensorService.getBySensorTypeId(sensorTypeId, pageable, langCode);
        return ResponseEntity.ok(ApiResponse.success("Type sensors retrieved successfully", result));
    }
    
    /**
     * Update sensor
     * 
     * @param id The sensor ID
     * @param dto The sensor update DTO
     * @param langCode Optional language code for translations
     * @return Updated sensor
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SensorDto>> update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateSensorDto dto,
        @RequestParam(required = false) String langCode
    ) {
        SensorDto result = sensorService.update(id, dto, langCode);
        return ResponseEntity.ok(ApiResponse.success("Sensor updated successfully", result));
    }
    
    /**
     * Delete sensor
     * 
     * @param id The sensor ID
     * @return Success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        sensorService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Sensor deleted successfully"));
    }
}
