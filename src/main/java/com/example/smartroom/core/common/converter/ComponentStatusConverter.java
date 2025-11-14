package com.example.smartroom.core.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;

import com.example.smartroom.core.common.enumeration.ComponentStatus;
import com.example.smartroom.core.common.exception.BadRequestException;

@Converter(autoApply = true)
public class ComponentStatusConverter implements AttributeConverter<ComponentStatus, String> {

    @Override
    public String convertToDatabaseColumn(ComponentStatus status) {
        return status != null ? status.getCode() : null;
    }

    @Override
    public ComponentStatus convertToEntityAttribute(String code) {
        if (code == null) return null;
        return Arrays.stream(ComponentStatus.values())
                .filter(s -> s.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Unknown component status code: " + code));
    }
}
