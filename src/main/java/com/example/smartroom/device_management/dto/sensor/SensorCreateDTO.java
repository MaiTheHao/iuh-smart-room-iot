package com.example.smartroom.device_management.dto.sensor;

import com.example.smartroom.common.constant.ValidationConstants;
import com.example.smartroom.common.enumeration.ComponentStatus;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SensorCreateDTO {
	
	@NotEmpty(message = "Sensor id must not be empty")
	@Size(max = ValidationConstants.MAX_ID_LENGTH, message = "Sensor id must not exceed " + ValidationConstants.MAX_ID_LENGTH + " characters")
	String id;
	
    @NotEmpty(message = "Sensor name must not be empty")
    @Size(max = ValidationConstants.MAX_NAME_LENGTH, message = "Sensor name must not exceed " + ValidationConstants.MAX_NAME_LENGTH + " characters")
    String name;
    
    @NotEmpty(message = "Sensor location must not be empty")
    @Size(max = ValidationConstants.MAX_LOCATION_LENGTH, message = "Sensor location must not exceed " + ValidationConstants.MAX_LOCATION_LENGTH + " characters")
    String location;
    
    @NotEmpty(message = "Device ID must not be null")
    String deviceId;

    ComponentStatus status = ComponentStatus.OFFLINE;
    
    String description;
}
