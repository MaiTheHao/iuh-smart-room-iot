package com.example.smartroom.modules.floor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.smartroom.modules.floor.entity.FloorLan;

public interface FloorLanRepository extends JpaRepository<FloorLan, Long> {
    List<FloorLan> findByFloorId(Long floorId);

    @Query("SELECT fl FROM FloorLan fl JOIN FETCH fl.language lang WHERE fl.floor.id = :floorId AND lang.code = :languageCode")
    Optional<FloorLan> findByFloorIdAndLanguageCode(Long floorId, String languageCode);
}
