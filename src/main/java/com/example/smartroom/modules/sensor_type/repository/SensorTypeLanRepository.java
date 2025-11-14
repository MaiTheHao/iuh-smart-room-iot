package com.example.smartroom.modules.sensor_type.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.smartroom.modules.sensor_type.entity.SensorTypeLan;
import java.util.List;
import java.util.Optional;

@Repository
public interface SensorTypeLanRepository extends JpaRepository<SensorTypeLan, Long> {
    
    @EntityGraph(attributePaths = {"sensorType", "language"})
    Optional<SensorTypeLan> findBySensorTypeIdAndLanguageCode(Long sensorTypeId, String languageCode);
    
    @Query("SELECT DISTINCT stl FROM SensorTypeLan stl " +
        "LEFT JOIN FETCH stl.language " +
        "WHERE stl.sensorType.id = :sensorTypeId")
    List<SensorTypeLan> findBySensorTypeId(Long sensorTypeId);
    
    void deleteBySensorTypeIdAndLanguageCode(Long sensorTypeId, String languageCode);
}
