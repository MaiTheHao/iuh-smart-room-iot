package com.example.smartroom.device_management.dto.device;

import com.example.smartroom.common.constant.ValidationConstants;
import com.example.smartroom.common.enumeration.ComponentStatus;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCreateDTO {
	
	@NotEmpty(message = "Device id must not be empty")
	@Size(max = ValidationConstants.MAX_ID_LENGTH, message = "Device id must not exceed " + ValidationConstants.MAX_ID_LENGTH + " characters")
	String id;

    @NotEmpty(message = "Device name must not be empty")
    @Size(max = ValidationConstants.MAX_NAME_LENGTH, message = "Device name must not exceed " + ValidationConstants.MAX_NAME_LENGTH + " characters")
    private String name;

    @NotEmpty(message = "Location must not be empty")
    @Size(max = ValidationConstants.MAX_LOCATION_LENGTH, message = "Location must not exceed " + ValidationConstants.MAX_LOCATION_LENGTH + " characters")
    private String location;

    @NotNull(message = "Hub ID must not be empty")
    private String hubId;

    private String description;

    private ComponentStatus status = ComponentStatus.OFFLINE;
}
