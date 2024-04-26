package com.example.focus_group.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@RequiredArgsConstructor
public enum Role {

    USER,
    ADMIN;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authority = new SimpleGrantedAuthority("ROLE" + this.name());
        return List.of(authority);
    }
}