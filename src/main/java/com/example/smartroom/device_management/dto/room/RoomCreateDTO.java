package com.example.smartroom.device_management.dto.room;

import com.example.smartroom.common.constant.ValidationConstants;

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
public class RoomCreateDTO {
	
	@NotEmpty(message = "Room id must not be empty")
	@Size(max = ValidationConstants.MAX_ID_LENGTH, message = "Room id must not exceed " + ValidationConstants.MAX_ID_LENGTH + " characters")
	String id;

    @NotEmpty(message = "Room name must not be empty")
    @Size(max = ValidationConstants.MAX_NAME_LENGTH, message = "Room name must not exceed " + ValidationConstants.MAX_NAME_LENGTH + " characters")
    String name;

    @NotEmpty(message = "Room location must not be empty")
    @Size(max = ValidationConstants.MAX_LOCATION_LENGTH, message = "Room location must not exceed " + ValidationConstants.MAX_LOCATION_LENGTH + " characters")
    String location;

    String description;
}
