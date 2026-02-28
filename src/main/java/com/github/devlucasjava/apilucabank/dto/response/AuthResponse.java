package com.github.devlucasjava.apilucabank.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Authentication response")
public class AuthResponse {

    @Schema(description = "JWT access token",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Token type", example = "Bearer")
    private String tokenType;

    @Schema(description = "Token expiration time in seconds", example = "86400")
    private Long expiresIn;

    @Schema(description = "User email", example = "user@email.com")
    private String email;

    @Schema(description = "User role", example = "ROLE_USER")
    private String role;
}