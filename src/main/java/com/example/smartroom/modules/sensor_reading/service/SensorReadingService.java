package com.example.smartroom.modules.sensor_reading.service;

import org.springframework.data.domain.Pageable;

import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.sensor_reading.dto.CreateBatchSensorReadingDto;
import com.example.smartroom.modules.sensor_reading.dto.CreateSensorReadingDto;
import com.example.smartroom.modules.sensor_reading.dto.SensorReadingDto;
import com.example.smartroom.modules.sensor_reading.dto.SensorReadingStatisticsDto;

import java.time.Instant;
import java.util.List;

public interface SensorReadingService {
    /**
     * Create a single sensor reading with client timestamp
     */
    SensorReadingDto create(CreateSensorReadingDto dto);
    
    /**
     * Create a single sensor reading with server auto-generated timestamp
     */
    SensorReadingDto createWithAutoTimestamp(com.example.smartroom.modules.sensor_reading.dto.CreateSensorReadingWithoutTimestampDto dto);
    
    /**
     * Create multiple sensor readings in batch
     */
    List<SensorReadingDto> createBatch(CreateBatchSensorReadingDto dto);
    
    /**
     * Get sensor reading by ID
     */
    SensorReadingDto getById(Long id);
    
    /**
     * Get all sensor readings with pagination
     */
    PaginationInfo<SensorReadingDto> getList(Pageable pageable);
    
    /**
     * Get readings by sensor ID with optional time range filter
     * @param startTime Optional start time filter
     * @param endTime Optional end time filter
     */
    PaginationInfo<SensorReadingDto> getBySensorId(
        Long sensorId, 
        Instant startTime, 
        Instant endTime, 
        Pageable pageable
    );
    
    /**
     * Get latest N readings for a sensor
     */
    List<SensorReadingDto> getLatestBySensorId(Long sensorId, int limit);
    
    /**
     * Get readings by multiple sensor IDs and time range
     */
    PaginationInfo<SensorReadingDto> getByMultipleSensors(
        List<Long> sensorIds,
        Instant startTime,
        Instant endTime,
        Pageable pageable
    );
    
    /**
     * Get statistics for a sensor within time range
     */
    SensorReadingStatisticsDto getStatistics(Long sensorId, Instant startTime, Instant endTime);
    
    /**
     * Delete sensor reading by ID
     */
    void delete(Long id);
    
    /**
     * Delete readings by sensor ID and optional time range
     * @param startTime Optional start time filter
     * @param endTime Optional end time filter
     * @return Number of deleted records
     */
    int deleteBySensorId(Long sensorId, Instant startTime, Instant endTime);
}
