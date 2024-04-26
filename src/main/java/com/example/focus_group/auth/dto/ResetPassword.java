package com.example.focus_group.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Reset password")
public record ResetPassword(
    @Schema(description = "Password", requiredMode = Schema.RequiredMode.REQUIRED, example = "MySecurePassword123@!", maxLength = 255)
    String password,
    @Schema(description = "Confirm new password", requiredMode = Schema.RequiredMode.REQUIRED, example = "MySecurePassword123@!", maxLength = 255)
    String confirmPassword
) {
    
}
