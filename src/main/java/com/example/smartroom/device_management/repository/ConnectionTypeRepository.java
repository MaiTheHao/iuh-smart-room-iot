package com.example.smartroom.device_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.smartroom.device_management.entity.ConnectionType;

@Repository
public interface ConnectionTypeRepository extends JpaRepository<ConnectionType, Long> {
    Optional<ConnectionType> findByCode(String code);
    
    boolean existsByCode(String code);
    
    boolean existsByName(String name);
}
