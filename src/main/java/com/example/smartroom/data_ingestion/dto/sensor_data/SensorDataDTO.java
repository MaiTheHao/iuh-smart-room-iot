package com.example.smartroom.data_ingestion.dto.sensor_data;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SensorDataDTO {
    
    Long id;

    Long value;

    Instant recordedAt;

    Instant createdAt;

    Instant updatedAt;

    Long sensorId;

    String sensorName;

    Long dataTypeId;

    String dataTypeName;

    String dataTypeUnit;
}
