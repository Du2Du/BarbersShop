package com.example.barbers_shop.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    SECRETARY("ROLE_SECRETARY"),
    BARBER("ROLE_BARBER");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public String getRoleName() {
        return this.name();
    }
}