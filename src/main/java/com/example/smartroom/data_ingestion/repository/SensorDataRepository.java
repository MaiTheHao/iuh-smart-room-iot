package com.example.smartroom.data_ingestion.repository;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.smartroom.data_ingestion.entity.SensorData;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

    Page<SensorData> findBySensorId(Long sensorId, Pageable pageable);

    Page<SensorData> findByDataTypeId(Long dataTypeId, Pageable pageable);

    @Query("SELECT sd FROM SensorData sd WHERE sd.sensor.id = :sensorId AND sd.dataType.id = :dataTypeId")
    Page<SensorData> findBySensorIdAndDataTypeId(
        @Param("sensorId") Long sensorId,
        @Param("dataTypeId") Long dataTypeId,
        Pageable pageable
    );

    @Query("SELECT sd FROM SensorData sd WHERE sd.sensor.id = :sensorId AND sd.recordedAt BETWEEN :startTime AND :endTime")
    Page<SensorData> findBySensorIdAndRecordedAtBetween(
        @Param("sensorId") Long sensorId,
        @Param("startTime") Instant startTime,
        @Param("endTime") Instant endTime,
        Pageable pageable
    );
}
