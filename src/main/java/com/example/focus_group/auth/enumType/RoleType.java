package com.example.focus_group.auth.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleType {
    USER("USER");
    private final String roleType;

}
