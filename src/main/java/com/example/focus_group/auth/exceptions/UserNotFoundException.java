package com.example.focus_group.auth.exceptions;


import com.example.focus_group.auth.enumType.ErrorCode;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private ErrorCode errorCode;

    public UserNotFoundException(ErrorCode errorType) {
        super(errorType.getMessage());
    }
}