package com.example.smartroom.device_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smartroom.device_management.entity.Room;

public interface RoomRepository extends JpaRepository<Room, String> {

}
