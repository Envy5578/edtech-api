package com.example.focus_group.auth.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import com.example.focus_group.auth.enumType.ErrorCode;

public class BadRequestException extends ResponseStatusException {
    public BadRequestException(ErrorCode error) {
        super(error.getStatusCode(), error.getMessage());
    }

    public BadRequestException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public BadRequestException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
