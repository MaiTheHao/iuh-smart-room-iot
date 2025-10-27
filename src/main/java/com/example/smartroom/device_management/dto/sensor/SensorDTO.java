package com.example.smartroom.device_management.dto.sensor;

import java.time.Instant;
import java.util.Set;

import com.example.smartroom.common.enumeration.ComponentStatus;
import com.example.smartroom.device_management.dto.sensor_data_type.SensorDataTypeDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SensorDTO {

    String id;

    String name;

    String location;

    ComponentStatus status;

    String description;

    Set<SensorDataTypeDTO> sensorDataTypes;

    String deviceId;

    Instant createdAt;

    Instant updatedAt;

    String createdBy;

    String updatedBy;

    Integer version;
}
