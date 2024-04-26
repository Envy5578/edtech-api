package com.example.focus_group.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public record UserDTO(
        @Schema(description = "First name", requiredMode = Schema.RequiredMode.REQUIRED, example = "John", maxLength = 255)
        String firstName,

        @Schema(description = "Last name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Doe", maxLength = 255)
        String lastName,

        @Schema(description = "Email address", requiredMode = Schema.RequiredMode.REQUIRED, example = "user@example.com", maxLength = 255)
        String email
) implements Serializable {
}