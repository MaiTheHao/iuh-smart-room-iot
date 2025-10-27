package com.example.smartroom.data_ingestion.dto.sensor_data;

import java.time.Instant;

import com.example.smartroom.common.constant.ValidationConstants;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SensorDataCreateDTO {
    
    @NotNull(message = "Sensor data value is required")
    @Min(value = 0, message = "Sensor data value must be greater than or equal to 0")
    @Max(value = ValidationConstants.MAX_SENSOR_DATA_VALUE, message = "Sensor data value must not exceed " + ValidationConstants.MAX_SENSOR_DATA_VALUE)
    Long value;
    
    @NotEmpty(message = "Sensor ID is required")
    String sensorId;

    @NotBlank(message = "Data type code is required")
    @Size(max = ValidationConstants.MAX_CODE_LENGTH, message = "Data type code must not exceed " + ValidationConstants.MAX_CODE_LENGTH + " characters")
    String dataTypeCode;

    @NotNull(message = "Recorded at timestamp is required")
    Instant recordedAt;
}
