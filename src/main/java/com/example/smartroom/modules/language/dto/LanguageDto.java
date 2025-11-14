package com.example.smartroom.modules.language.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class LanguageDto {
    private Long id;
    private String code;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
}
