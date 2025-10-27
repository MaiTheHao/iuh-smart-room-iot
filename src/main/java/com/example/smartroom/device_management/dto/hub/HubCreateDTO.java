package com.example.smartroom.device_management.dto.hub;

import com.example.smartroom.common.constant.ValidationConstants;
import com.example.smartroom.common.enumeration.ComponentStatus;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class HubCreateDTO {
	
	@NotEmpty(message = "Hub id must not be empty")
	@Size(max = ValidationConstants.MAX_ID_LENGTH, message = "Hub id must not exceed " + ValidationConstants.MAX_ID_LENGTH + " characters")
	String id;

    @NotEmpty(message = "Hub name must not be empty")
    @Size(max = ValidationConstants.MAX_NAME_LENGTH, message = "Hub name must not exceed " + ValidationConstants.MAX_NAME_LENGTH + " characters")
    String name;

    @NotEmpty(message = "Location must not be empty")
    @Size(max = ValidationConstants.MAX_LOCATION_LENGTH, message = "Location must not exceed " + ValidationConstants.MAX_LOCATION_LENGTH + " characters")
    String location;

    @NotEmpty(message = "Room ID must not be empty")
    String roomId;

    String description;

    ComponentStatus status = ComponentStatus.OFFLINE;
}
