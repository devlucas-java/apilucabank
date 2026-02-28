package com.github.devlucasjava.apilucabank.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response Login")
public class LoginRequest {
    @Schema(description = "User email or username", example = "user@email.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Login is required")
    private String login;

    @Schema(description = "User password", example = "Password123!",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Password is required")
    private String password;
}
