package com.tritronik.hiringtest.smart_home_stay.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    EMPLOYEE;

    @Override
    public String getAuthority() {
        return name();
    }
}
