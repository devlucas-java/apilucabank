package com.github.devlucasjava.apilucabank.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Optional parameters to filter users in search. All fields are optional.")
public record UsersFilterRequest(

        @Schema(description = "User ID", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String id,

        @Schema(description = "User first name", example = "Lucas", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String firstName,

        @Schema(description = "User last name", example = "Silva", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String lastName,

        @Schema(description = "User passport or national ID (CPF/CNPJ)", example = "12345678901", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String passport,

        @Schema(description = "Role name of the user", example = "ROLE_USER", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String role,

        @Schema(description = "Whether the user is active", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Boolean isActive,

        @Schema(description = "Whether the user is locked", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Boolean isLocked,

        @Schema(description = "User creation date", example = "2024-01-15", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        LocalDate createdAt,

        @Schema(description = "User last update date", example = "2024-03-01", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        LocalDate updatedAt
) {}