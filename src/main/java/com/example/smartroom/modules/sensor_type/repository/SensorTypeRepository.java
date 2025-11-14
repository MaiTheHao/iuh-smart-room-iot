package com.example.smartroom.modules.sensor_type.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.smartroom.modules.sensor_type.entity.SensorType;
import java.util.Optional;

@Repository
public interface SensorTypeRepository extends JpaRepository<SensorType, Long> {
    
    @EntityGraph(attributePaths = {"sensorTypeLans", "sensorTypeLans.language"})
    @NonNull
    Page<SensorType> findAll(@NonNull Pageable pageable);
    
    @EntityGraph(attributePaths = {"sensorTypeLans", "sensorTypeLans.language"})
    @NonNull
    Optional<SensorType> findById(@NonNull Long id);
    
    @EntityGraph(attributePaths = {"sensorTypeLans", "sensorTypeLans.language"})
    Optional<SensorType> findByCode(String code);
}
