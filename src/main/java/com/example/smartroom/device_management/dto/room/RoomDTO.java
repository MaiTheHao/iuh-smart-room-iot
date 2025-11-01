package com.example.smartroom.device_management.dto.room;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RoomDTO{

    String id; 

    String name; 

    String location; 

    String description; 

    Instant createdAt; 

    Instant updatedAt; 

    Long createdBy; 

    Long updatedBy; 

    Integer version;
}