package com.example.smartroom.device_management.dto.device;

import java.time.Instant;

import com.example.smartroom.common.enumeration.ComponentStatus;

import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class DeviceDTO {

    String id;

    String name;

    String location;

    ComponentStatus status;

    String description;

    String hubId;

    Instant createdAt;

    Instant updatedAt;

    Long createdBy;

    Long updatedBy;
 
    Integer version;
}
