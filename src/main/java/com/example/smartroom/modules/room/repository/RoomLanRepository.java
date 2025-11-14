package com.example.smartroom.modules.room.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.smartroom.modules.room.entity.RoomLan;

public interface RoomLanRepository extends JpaRepository<RoomLan, Long> {
    List<RoomLan> findByRoomId(Long roomId);

    @Query("SELECT rl FROM RoomLan rl JOIN FETCH rl.language lang WHERE rl.room.id = :roomId AND lang.code = :languageCode")
    Optional<RoomLan> findByRoomIdAndLanguageCode(Long roomId, String languageCode);
}
