package com.example.focus_group.auth.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RegistrationRequest(
        @Schema(description = "First name", requiredMode = Schema.RequiredMode.REQUIRED, example = "John", maxLength = 255)
        String firstName,

        @Schema(description = "Last name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Doe", maxLength = 255)
        String lastName,

        @Schema(description = "Email address", requiredMode = Schema.RequiredMode.REQUIRED, example = "user@example.com", maxLength = 255)
        String email,

        @Schema(description = "Password", requiredMode = Schema.RequiredMode.REQUIRED, example = "MySecurePassword123@!", maxLength = 255)
        @NotBlank(message = "Password cannot be blank")
        @Length(min = 8, max = 255, message = "Password length must be between 8 and 255 characters")
        String password
) implements Serializable {
}
