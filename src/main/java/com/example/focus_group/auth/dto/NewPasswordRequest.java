package com.example.focus_group.auth.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request for resetting password")
public record NewPasswordRequest(
        
        @Schema(description = "Current password", requiredMode = Schema.RequiredMode.REQUIRED, example = "MySecurePassword123@!", maxLength = 255)
        @NotBlank(message = "Current password cannot be blank")
        @Length(max = 255, message = "Current password length must be less than or equal to 255 characters")
        @JsonProperty(required = true)
        String currentPassword,


        @Schema(minLength = 8, maxLength = 255, description = "New password", requiredMode = Schema.RequiredMode.REQUIRED, example = "MySecurePassword123@!", pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
        @NotBlank(message = "Password cannot be blank")
        @Length(min = 8, max = 255, message = "Password length must be less than or equal to 255 characters")
        @JsonProperty(required = true)
        String password,

        @Schema(minLength = 8, description = "Confirm new password", requiredMode = Schema.RequiredMode.REQUIRED, example = "MySecurePassword123@!", maxLength = 255)
        @NotBlank(message = "Confirm password cannot be blank")
        @Length(min = 8, max = 255, message = "Confirm password length must be less than or equal to 255 characters")
        @JsonProperty(required = true)
        String confirmPassword
) implements Serializable {
}
