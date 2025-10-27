package com.example.smartroom.device_management.dto.data_type;

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
public class DataTypeCreateDTO {

    @NotEmpty(message = "Data type code must not be empty")
    @Size(max = ValidationConstants.MAX_CODE_LENGTH, message = "Data type code must not exceed " + ValidationConstants.MAX_CODE_LENGTH + " characters")
    String code;
    
    @NotEmpty(message = "Data type name must not be empty")
    @Size(max = ValidationConstants.MAX_NAME_LENGTH, message = "Data type name must not exceed " + ValidationConstants.MAX_NAME_LENGTH + " characters")
    String name;
    
    @NotEmpty(message = "Data type unit must not be empty")
    @Size(max = ValidationConstants.MAX_UNIT_LENGTH, message = "Data type unit must not exceed " + ValidationConstants.MAX_UNIT_LENGTH + " characters")
    String unit;

    String description;
}
