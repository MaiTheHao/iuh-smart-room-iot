package com.example.smartroom.modules.floor.dto;

import lombok.Data;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;

@Data
public class FloorLanDto {
    private Long id;

	@NotBlank
	private String name;

	private String description;
	private String locationDescription;
    
	@NotBlank
	private String langCode;

	private Instant createdAt;
	private Instant updatedAt;
}
