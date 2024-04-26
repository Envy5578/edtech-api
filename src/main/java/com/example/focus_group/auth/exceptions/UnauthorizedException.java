package com.example.focus_group.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

import com.example.focus_group.auth.enumType.ErrorCode;

import lombok.Getter;

@Getter
public class UnauthorizedException extends AuthenticationException {

    private ErrorCode errorCode;

    public UnauthorizedException(ErrorCode errorType) {
        super(errorType.getMessage());
    }


}
