package com.example.smartroom.modules.sensor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.smartroom.modules.sensor.entity.Sensor;
import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
	@EntityGraph(attributePaths = {"sensorLans", "sensorLans.language", "device", "sensorType"})
	@NonNull
	Page<Sensor> findAll(@NonNull Pageable pageable);
    
    @EntityGraph(attributePaths = {"sensorLans", "sensorLans.language", "device", "sensorType"})
	@NonNull
    Optional<Sensor> findById(@NonNull Long id);
    
    @Query(value = "SELECT DISTINCT s FROM Sensor s LEFT JOIN FETCH s.sensorLans sl " +
		"LEFT JOIN FETCH sl.language WHERE s.device.id = :deviceId",
        countQuery = "SELECT COUNT(s) FROM Sensor s WHERE s.device.id = :deviceId")
    @NonNull
    Page<Sensor> findByDeviceId(@NonNull Long deviceId, @NonNull Pageable pageable);
    
    @Query(value = "SELECT DISTINCT s FROM Sensor s LEFT JOIN FETCH s.sensorLans sl " +
        "LEFT JOIN FETCH sl.language WHERE s.sensorType.id = :sensorTypeId",
        countQuery = "SELECT COUNT(s) FROM Sensor s WHERE s.sensorType.id = :sensorTypeId")
    @NonNull
    Page<Sensor> findBySensorTypeId(@NonNull Long sensorTypeId, @NonNull Pageable pageable);
}
