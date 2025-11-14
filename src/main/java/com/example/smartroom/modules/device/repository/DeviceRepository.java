package com.example.smartroom.modules.device.repository;

import com.example.smartroom.modules.device.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    
    @EntityGraph(attributePaths = {"deviceLans", "deviceLans.language", "room", "gateway"})
    @NonNull
    Page<Device> findAll(@NonNull Pageable pageable);
    
    @EntityGraph(attributePaths = {"deviceLans", "deviceLans.language", "room", "gateway"})
    @NonNull
    Optional<Device> findById(@NonNull Long id);
    
    @Query("SELECT DISTINCT d FROM Device d " +
           "LEFT JOIN FETCH d.deviceLans dl " +
           "LEFT JOIN FETCH dl.language " +
           "LEFT JOIN FETCH d.room " +
           "LEFT JOIN FETCH d.gateway " +
           "WHERE d.isGateway = true")
    Page<Device> findAllGateways(Pageable pageable);
    
    @Query("SELECT DISTINCT d FROM Device d " +
           "LEFT JOIN FETCH d.deviceLans dl " +
           "LEFT JOIN FETCH dl.language " +
           "LEFT JOIN FETCH d.room " +
           "LEFT JOIN FETCH d.gateway " +
           "WHERE d.gateway.id = :gatewayId")
    Page<Device> findByGatewayId(@Param("gatewayId") Long gatewayId, Pageable pageable);
    
    @EntityGraph(attributePaths = {"deviceLans", "deviceLans.language", "room", "gateway"})
    @Query("SELECT d FROM Device d WHERE d.mac = :mac")
    Optional<Device> findByMac(@Param("mac") String mac);
}
