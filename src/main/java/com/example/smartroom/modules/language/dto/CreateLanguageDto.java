package com.example.smartroom.modules.language.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateLanguageDto {
    @NotBlank(message = "Language code is required")
    @Size(min = 2, max = 5, message = "Language code must be between 2 and 5 characters")
    @Pattern(regexp = "^[a-z]{2,5}$", message = "Language code must be lowercase letters only")
    private String code;

    @NotBlank(message = "Language name is required")
    private String name;
}
