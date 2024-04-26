package com.example.focus_group.auth.enumType;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNAUTHORIZED_ACCESS("Unauthorized access", HttpStatusCode.valueOf(401)),
    INVALID_CREDENTIALS("Invalid credentials", HttpStatusCode.valueOf(401)),
    EXPIRED_TOKEN("Expired token", HttpStatusCode.valueOf(401)),
    INVALID_ACCESS_TOKEN("Invalid access token", HttpStatusCode.valueOf(401)),
    EMAIL_NOT_VERIFIED("Email not verified", HttpStatusCode.valueOf(403)),
    EMAIL_ALREADY_REGISTERED("Email already registered", HttpStatusCode.valueOf(403));
    @Getter
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

}
