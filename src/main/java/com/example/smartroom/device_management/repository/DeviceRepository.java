package com.example.smartroom.device_management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.smartroom.device_management.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, String> {
    @Query("SELECT d FROM Device d WHERE d.hub.id = :hubId")
    public Page<Device> findAllByHubId(@Param("hubId") String hubId, Pageable pageable);

    @Query("SELECT COUNT(d) FROM Device d WHERE d.hub.id = :hubId")
    public Long countByHubId(@Param("hubId") String hubId);

    @Query("SELECT COUNT(d) FROM Device d WHERE d.hub.room.id = :roomId")
    public Long countByRoomId(@Param("roomId") String roomId);
}
