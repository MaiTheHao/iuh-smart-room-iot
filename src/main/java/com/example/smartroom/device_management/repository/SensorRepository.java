package com.example.smartroom.device_management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.smartroom.common.enumeration.ComponentStatus;
import com.example.smartroom.device_management.dto.sensor.SensorStatisticsDTO;
import com.example.smartroom.device_management.entity.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String> {
    Optional<Sensor> findByName(String name);
    
    List<Sensor> findByStatus(ComponentStatus status);
    
    List<Sensor> findByLocation(String location);
    
    @Query("SELECT s FROM Sensor s LEFT JOIN FETCH s.sensorDataTypes WHERE s.id = :id")
    Optional<Sensor> findByIdWithDataTypes(@Param("id") String id);

    @Query("SELECT s FROM Sensor s WHERE s.device.id = :deviceId")
    Page<Sensor> findByDeviceId(@Param("deviceId") String deviceId, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Sensor s WHERE s.device.id = :deviceId")
    Long countByDeviceId(@Param("deviceId") String deviceId);

    @Query("SELECT COUNT(s) FROM Sensor s WHERE s.device.hub.id = :hubId")
    Long countByHubId(@Param("hubId") String hubId);

    @Query("SELECT COUNT(s) FROM Sensor s WHERE s.device.hub.room.id = :roomId")
    Long countByRoomId(@Param("roomId") String roomId);
    
    boolean existsByName(String name);

    @Query(
        "SELECT new com.example.smartroom.device_management.dto.sensor.SensorStatisticsDTO(s.id, s.name, s.location, s.description, s.status, s.device.id, COUNT(DISTINCT sdt.dataType.id)) " + 
        "FROM Sensor s LEFT JOIN s.sensorDataTypes sdt " + 
        "WHERE s.id = :sensorId " +
        "GROUP BY s.id, s.name, s.location, s.description, s.status, s.device.id"
    )
    SensorStatisticsDTO getSensorStatisticsById(@Param("sensorId") String sensorId);
}
