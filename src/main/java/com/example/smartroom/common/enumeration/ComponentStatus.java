package com.example.smartroom.common.enumeration;

import com.example.smartroom.common.exception.BadRequestException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum ComponentStatus {
    ONLINE("online"),
    OFFLINE("offline"),
    INITIALIZING("initializing"),
    ERROR("error"),
    DISABLED("disabled");

    private final String code;

    ComponentStatus(String code) {
        this.code = code;
    }

    @JsonCreator
    public static ComponentStatus fromCode(String code) {
        for (ComponentStatus status : values()) {
            if (status.code.equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new BadRequestException("Unknown ComponentStatus code: " + code);
    }

    @JsonValue
    public String toCode() {
        return code;
    }
}
