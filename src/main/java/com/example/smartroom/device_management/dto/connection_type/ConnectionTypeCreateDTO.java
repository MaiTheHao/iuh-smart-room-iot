package com.example.smartroom.device_management.dto.connection_type;

import com.example.smartroom.common.constant.ValidationConstants;

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
public class ConnectionTypeCreateDTO {

    @NotEmpty(message = "Connection type code must not be empty")
    @Size(max = ValidationConstants.MAX_CODE_LENGTH, message = "Connection type code must not exceed" + ValidationConstants.MAX_CODE_LENGTH + " characters")
    String code;
    
    @NotEmpty(message = "Connection type name must not be empty")
    @Size(max = ValidationConstants.MAX_NAME_LENGTH, message = "Connection type name must not exceed" + ValidationConstants.MAX_NAME_LENGTH + " characters")
    String name;
    
    String description;
}
