package com.example.smartroom.device_management.dto.sensor_data_type;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SensorDataTypeDTO {
    
    String sensorId;

    Long dataTypeId;

    String dataTypeName;

    String dataTypeCode;

    String dataTypeUnit;

    Instant createdAt;

    Instant updatedAt;
}
