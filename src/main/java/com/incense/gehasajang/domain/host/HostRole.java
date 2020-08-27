package com.incense.gehasajang.domain.host;

import lombok.Getter;

@Getter
public enum HostRole {

    ROLE_MAIN("ROLE_MAIN","main"),
    ROLE_SUB("ROLE_SUB","sub");

    private final String role;
    private final String type;

    HostRole(String role, String type) {
        this.role = role;
        this.type = type;
    }

}
