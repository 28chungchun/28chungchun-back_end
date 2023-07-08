package com.example.cheongchun28.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEntity {
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    EMPLOYEE("EMPLOYEE"),
    USER("USER");

    private final String role;

    public String value() {
        return role;
    }
}
