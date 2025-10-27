package com.example.smartroom.device_management.dto.sensor_data_type;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SensorDataTypeCreateDTO {

    @NotEmpty(message = "Sensor ID must not be empty")
    String sensorId;

    @NotNull(message = "Data type ID must not be null")
    Long dataTypeId;
}
