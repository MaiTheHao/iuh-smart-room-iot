package com.example.smartroom.modules.sensor_type.service;

import org.springframework.data.domain.Pageable;

import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.sensor_type.dto.CreateSensorTypeDto;
import com.example.smartroom.modules.sensor_type.dto.SensorTypeDto;
import com.example.smartroom.modules.sensor_type.dto.UpdateSensorTypeDto;

public interface SensorTypeService {
    /**
     * Create new sensor type with unique code
     * Throws InternalServerException if code already exists
     * 
     * @param dto CreateSensorTypeDto with code
     * @return SensorTypeDto
     * @throws InternalServerException if code already exists
     */
    SensorTypeDto create(CreateSensorTypeDto dto);
    
    /**
     * Update sensor type code with optional language filter
     * 
     * @param id SensorType ID
     * @param dto UpdateSensorTypeDto with new code
     * @param langCode Optional language code to flatten translation in response
     * @return SensorTypeDto
     */
    SensorTypeDto update(Long id, UpdateSensorTypeDto dto, String langCode);
    
    /**
     * Delete sensor type and cascade delete all translations
     * 
     * @param id SensorType ID
     * @throws NotFoundException if SensorType not found
     */
    void delete(Long id);
    
    /**
     * Get sensor type by ID with optional language filter
     * 
     * @param id SensorType ID
     * @param langCode Optional language code to flatten translation
     * @return SensorTypeDto
     * @throws NotFoundException if SensorType not found
     */
    SensorTypeDto getById(Long id, String langCode);
    
    /**
     * Get sensor type by code with optional language filter
     * 
     * @param code SensorType code
     * @param langCode Optional language code to flatten translation
     * @return SensorTypeDto
     * @throws NotFoundException if SensorType not found
     */
    SensorTypeDto getByCode(String code, String langCode);
    
    /**
     * Get all sensor types with pagination and optional language filter
     * 
     * @param pageable Pagination parameters (page, size, sort)
     * @param langCode Optional language code to flatten translation
     * @return PaginationInfo with SensorTypeDto list
     */
    PaginationInfo<SensorTypeDto> getList(Pageable pageable, String langCode);
}
