package com.example.smartroom.device_management.dto.hub;

import java.time.Instant;

import com.example.smartroom.common.enumeration.ComponentStatus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class HubDTO {
    
    String id;

    String name;

    String location;

    ComponentStatus status;

    String description;

    String roomId;

    Instant createdAt;

    Instant updatedAt;

    Long createdBy;

    Long updatedBy;

    Integer version;
}
