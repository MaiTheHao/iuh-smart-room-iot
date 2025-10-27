package com.example.smartroom.common.enumeration;

import lombok.Getter;

@Getter
public enum ComponentStatus {
    ONLINE("online"),
    OFFLINE("offline"),
    INITIALIZING("initializing"),
    ERROR("error"),
    DISABLED("disabled");

    private final String code;
    private ComponentStatus(String code) {
        this.code = code;
    }
}
