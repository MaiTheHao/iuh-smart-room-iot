package com.example.smartroom.modules.room.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import com.example.smartroom.modules.device.entity.Device;
import com.example.smartroom.modules.room.entity.Room;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @EntityGraph(attributePaths = {"roomLans", "roomLans.language", "floor"})
    @NonNull
    Page<Room> findAll(@NonNull Pageable pageable);

    @EntityGraph(attributePaths = {"roomLans", "roomLans.language", "floor"})
    @NonNull
    Optional<Room> findById(@NonNull Long id);

    @Query(value = "SELECT DISTINCT d FROM Device d " +
                   "LEFT JOIN FETCH d.deviceLans dl " +
                   "LEFT JOIN FETCH dl.language " +
                   "WHERE d.room.id = :roomId",
           countQuery = "SELECT COUNT(DISTINCT d) FROM Device d WHERE d.room.id = :roomId")
    @NonNull
    Page<Device> findDevicesByRoomId(@NonNull Long roomId, @NonNull Pageable pageable);
}
