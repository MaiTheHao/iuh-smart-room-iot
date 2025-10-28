package com.example.smartroom.device_management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.smartroom.device_management.dto.hub.HubStatisticsDTO;
import com.example.smartroom.device_management.entity.Hub;

public interface HubRepository extends JpaRepository<Hub, String>{
    @Query("SELECT h FROM Hub h WHERE h.room.id = :roomId")
    Page<Hub> findByRoomId(@Param("roomId") String roomId, Pageable pageable);

    @Query("SELECT COUNT(h) FROM Hub h WHERE h.room.id = :roomId")
    Long countByRoomId(@Param("roomId") String roomId);

    @Query(
        "SELECT new com.example.smartroom.device_management.dto.hub.HubStatisticsDTO(h.id, h.name, h.location, h.description, COUNT(DISTINCT d.id), COUNT(DISTINCT s.id)) " +
        "FROM Hub h LEFT JOIN h.devices d LEFT JOIN d.sensors s " +
        "WHERE h.id = :hubId " +
        "GROUP BY h.id, h.name, h.location, h.description"
    )
    HubStatisticsDTO getHubStatisticsById(@Param("hubId") String hubId);
}