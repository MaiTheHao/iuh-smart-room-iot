package com.example.smartroom.modules.device.repository;

import com.example.smartroom.modules.device.entity.DeviceLan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceLanRepository extends JpaRepository<DeviceLan, Long> {
    
    List<DeviceLan> findByDeviceId(Long deviceId);
    
    @Query("SELECT dl FROM DeviceLan dl JOIN FETCH dl.language lang WHERE dl.device.id = :deviceId AND lang.code = :languageCode")
    Optional<DeviceLan> findByDeviceIdAndLanguageCode(@Param("deviceId") Long deviceId, @Param("languageCode") String languageCode);
}
