package com.example.smartroom.device_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.smartroom.device_management.entity.DataType;

@Repository
public interface DataTypeRepository extends JpaRepository<DataType, Long> {
    Optional<DataType> findByCode(String code);
    
    boolean existsByCode(String code);
    
    boolean existsByName(String name);
}
