package com.example.smartroom.modules.sensor_reading.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.smartroom.modules.sensor_reading.entity.SensorReading;

@Repository
public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {
    
    @EntityGraph(attributePaths = {"sensor"})
    @NonNull
    Page<SensorReading> findAll(@NonNull Pageable pageable);
    
    @EntityGraph(attributePaths = {"sensor"})
    @NonNull
    Optional<SensorReading> findById(@NonNull Long id);

    /**
     * Find all readings for a specific sensor
     */
    @Query(value = "SELECT DISTINCT sr FROM SensorReading sr " +
                   "LEFT JOIN FETCH sr.sensor " +
                   "WHERE sr.sensor.id = :sensorId " +
                   "ORDER BY sr.timestamp DESC",
           countQuery = "SELECT COUNT(sr) FROM SensorReading sr WHERE sr.sensor.id = :sensorId")
    @NonNull
    Page<SensorReading> findBySensorId(@NonNull Long sensorId, @NonNull Pageable pageable);

    /**
     * Find readings by sensor and time range
     */
    @Query(value = "SELECT DISTINCT sr FROM SensorReading sr " +
                   "LEFT JOIN FETCH sr.sensor " +
                   "WHERE sr.sensor.id = :sensorId " +
                   "AND sr.timestamp BETWEEN :startTime AND :endTime " +
                   "ORDER BY sr.timestamp DESC",
           countQuery = "SELECT COUNT(sr) FROM SensorReading sr " +
                        "WHERE sr.sensor.id = :sensorId " +
                        "AND sr.timestamp BETWEEN :startTime AND :endTime")
    @NonNull
    Page<SensorReading> findBySensorIdAndTimestampBetween(
        @NonNull Long sensorId, 
        @NonNull Instant startTime, 
        @NonNull Instant endTime, 
        @NonNull Pageable pageable
    );

    /**
     * Find latest N readings for a sensor
     */
    @Query("SELECT sr FROM SensorReading sr " +
           "LEFT JOIN FETCH sr.sensor " +
           "WHERE sr.sensor.id = :sensorId " +
           "ORDER BY sr.timestamp DESC")
    @NonNull
    List<SensorReading> findLatestBySensorId(@NonNull Long sensorId, @NonNull Pageable pageable);

    /**
     * Find readings by multiple sensor IDs and time range
     */
    @Query(value = "SELECT DISTINCT sr FROM SensorReading sr " +
                   "LEFT JOIN FETCH sr.sensor " +
                   "WHERE sr.sensor.id IN :sensorIds " +
                   "AND sr.timestamp BETWEEN :startTime AND :endTime " +
                   "ORDER BY sr.timestamp DESC",
           countQuery = "SELECT COUNT(sr) FROM SensorReading sr " +
                        "WHERE sr.sensor.id IN :sensorIds " +
                        "AND sr.timestamp BETWEEN :startTime AND :endTime")
    @NonNull
    Page<SensorReading> findBySensorIdsAndTimestampBetween(
        @NonNull List<Long> sensorIds,
        @NonNull Instant startTime,
        @NonNull Instant endTime,
        @NonNull Pageable pageable
    );

    /**
     * Get statistics for a sensor within time range
     * Using native query for better control over result type
     */
    @Query(value = "SELECT " +
           "COUNT(*), " +
           "AVG(temp_c), MIN(temp_c), MAX(temp_c), " +
           "AVG(volt), MIN(volt), MAX(volt), " +
           "AVG(ampe), MIN(ampe), MAX(ampe), " +
           "AVG(watt), MIN(watt), MAX(watt), " +
           "SUM(watt_hour), AVG(watt_hour), " +
           "AVG(hz), MIN(hz), MAX(hz), " +
           "AVG(power_factor), MIN(power_factor), MAX(power_factor) " +
           "FROM sensor_reading " +
           "WHERE sensor_id = :sensorId " +
           "AND timestamp BETWEEN :startTime AND :endTime",
           nativeQuery = true)
    Object[] getStatisticsBySensorIdAndTimestampBetween(
        @Param("sensorId") Long sensorId,
        @Param("startTime") Instant startTime,
        @Param("endTime") Instant endTime
    );

    /**
     * Delete readings older than specified timestamp
     */
    @Modifying
    @Query("DELETE FROM SensorReading sr WHERE sr.timestamp < :timestamp")
    int deleteByTimestampBefore(@NonNull Instant timestamp);

    /**
     * Delete readings by sensor ID and time range
     */
    @Modifying
    @Query("DELETE FROM SensorReading sr " +
           "WHERE sr.sensor.id = :sensorId " +
           "AND sr.timestamp BETWEEN :startTime AND :endTime")
    int deleteBySensorIdAndTimestampBetween(
        @NonNull Long sensorId,
        @NonNull Instant startTime,
        @NonNull Instant endTime
    );
}
