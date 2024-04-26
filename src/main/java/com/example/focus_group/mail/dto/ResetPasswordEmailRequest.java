package com.example.focus_group.mail.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to reset password")
public record ResetPasswordEmailRequest(
        @Schema(description = "Email address", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 255, example = "user@example.com")
        @NotBlank(message = "Email cannot be blank")
        @Length(max = 255, message = "Email length must be less than or equal to 255 characters")
        @Email(message = "Invalid email format")
        String email
) implements Serializable {
}