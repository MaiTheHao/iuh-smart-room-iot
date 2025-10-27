package com.example.smartroom.device_management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.smartroom.device_management.entity.SensorDataType;
import com.example.smartroom.device_management.entity.id.SensorDataTypeId;

@Repository
public interface SensorDataTypeRepository extends JpaRepository<SensorDataType, SensorDataTypeId> {
    @Query("SELECT sdt FROM SensorDataType sdt WHERE sdt.sensor.id = :sensorId")
    List<SensorDataType> findBySensorId(@Param("sensorId") String sensorId, Pageable pageRequest);
    
    @Query("SELECT sdt FROM SensorDataType sdt WHERE sdt.dataType.id = :dataTypeId")
    List<SensorDataType> findByDataTypeId(@Param("dataTypeId") Long dataTypeId, Pageable pageRequest);
    
    @Query("SELECT sdt FROM SensorDataType sdt WHERE sdt.sensor.id = :sensorId AND sdt.dataType.id = :dataTypeId")
    Optional<SensorDataType> findBySensorIdAndDataTypeId(
        @Param("sensorId") String sensorId, 
        @Param("dataTypeId") Long dataTypeId
    );
    
    @Query("SELECT CASE WHEN COUNT(sdt) > 0 THEN true ELSE false END FROM SensorDataType sdt WHERE sdt.sensor.id = :sensorId AND sdt.dataType.id = :dataTypeId")
    boolean existsBySensorIdAndDataTypeId(
        @Param("sensorId") String sensorId, 
        @Param("dataTypeId") Long dataTypeId
    );
}
