package com.example.smartroom.modules.sensor.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.smartroom.modules.sensor.entity.SensorLan;
import java.util.List;
import java.util.Optional;

@Repository
public interface SensorLanRepository extends JpaRepository<SensorLan, Long> {
    
    @EntityGraph(attributePaths = {"sensor", "language"})
    Optional<SensorLan> findBySensorIdAndLanguageCode(Long sensorId, String languageCode);
    
    @Query("SELECT DISTINCT sl FROM SensorLan sl " +
           "LEFT JOIN FETCH sl.language " +
           "WHERE sl.sensor.id = :sensorId")
    List<SensorLan> findBySensorId(Long sensorId);
    
    void deleteBySensorIdAndLanguageCode(Long sensorId, String languageCode);
}
