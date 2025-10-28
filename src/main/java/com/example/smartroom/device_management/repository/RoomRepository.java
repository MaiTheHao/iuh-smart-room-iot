package com.example.smartroom.device_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.smartroom.device_management.dto.room.RoomStatisticsDTO;
import com.example.smartroom.device_management.entity.Room;

public interface RoomRepository extends JpaRepository<Room, String>, JpaSpecificationExecutor<Room> {
    @Query(
        "SELECT new com.example.smartroom.device_management.dto.room.RoomStatisticsDTO(r.id, r.name, r.location, r.description, COUNT(DISTINCT h.id), COUNT(DISTINCT d.id), COUNT(DISTINCT s.id), r.createdAt, r.updatedAt, r.createdBy, r.updatedBy, r.version) " +
        "FROM Room r LEFT JOIN r.hubs h LEFT JOIN h.devices d LEFT JOIN d.sensors s " +
        "WHERE r.id = :roomId " +
        "GROUP BY r.id, r.name, r.location, r.description, r.createdAt, r.updatedAt, r.createdBy, r.updatedBy, r.version"
    )
    public RoomStatisticsDTO getRoomStatisticsById(@Param("roomId") String roomId);
}
