package com.example.focus_group.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response for successful authentication")
public record AuthenticationResponse(
        @Schema(description = "Token type", example = "bearer")
        String tokenType,
        @Schema(description = "Access token", requiredMode = Schema.RequiredMode.REQUIRED)
        String accessToken,
        @Schema(description = "Time in minutes to expire access token", example = "30")
        Integer expires_in,
        @Schema(description = "Refresh token", requiredMode = Schema.RequiredMode.REQUIRED)
        String refreshToken

)  {}
