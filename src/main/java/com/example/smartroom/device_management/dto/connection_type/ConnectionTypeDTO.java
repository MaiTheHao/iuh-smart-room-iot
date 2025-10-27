package com.example.smartroom.device_management.dto.connection_type;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ConnectionTypeDTO {
    
    Long id;

    String code;

    String name;

    String description;

    Instant createdAt;

    Instant updatedAt;

    String createdBy;

    String updatedBy;

    Integer version;
}
