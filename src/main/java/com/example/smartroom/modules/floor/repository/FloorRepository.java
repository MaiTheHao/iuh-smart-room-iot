package com.example.smartroom.modules.floor.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import com.example.smartroom.modules.floor.entity.Floor;
import com.example.smartroom.modules.room.entity.Room;

public interface FloorRepository extends JpaRepository<Floor, Long> {
    
    @EntityGraph(attributePaths = {"floorLans", "floorLans.language"})
    @NonNull
    Page<Floor> findAll(@NonNull Pageable pageable);
    
    @EntityGraph(attributePaths = {"floorLans", "floorLans.language"})
    @NonNull
    Optional<Floor> findById(@NonNull Long id);

    @Query(value = "SELECT DISTINCT rm FROM Room rm " +
                   "LEFT JOIN FETCH rm.roomLans rl " +
                   "LEFT JOIN FETCH rl.language " +
                   "WHERE rm.floor.id = :floorId",
           countQuery = "SELECT COUNT(DISTINCT rm) FROM Room rm WHERE rm.floor.id = :floorId")
    @NonNull
    Page<Room> findRoomsByFloorId(@NonNull Long floorId, @NonNull Pageable pageable);
}
