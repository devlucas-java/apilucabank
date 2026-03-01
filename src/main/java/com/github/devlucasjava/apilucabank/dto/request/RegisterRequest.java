package com.github.devlucasjava.apilucabank.dto.request;

import com.github.devlucasjava.apilucabank.dto.utils.annotation.ValidAge;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request Register")
public class RegisterRequest {

    @Schema(description = "User first name", example = "John",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Schema(description = "User last name", example = "Doe",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    @Schema(description = "User email", example = "john@email.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format (ISO standard)",
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @Schema(description = "Passport number (CPF for users, CNPJ for enterprises)",
            example = "12345678901",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Passaport is required")
    @Size(min = 5, max = 20, message = "Passaport must be between 5 and 20 characters")
    private String passport;

    @Schema(description = "User password", example = "Password123!",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&!+=]).*$",
            message = "Password must contain at least: 1 uppercase, 1 lowercase, 1 number and 1 special character")
    private String password;

    @Schema(description = "User birth date", example = "1990-01-15",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    @ValidAge(message = "User must be older than 18 years")
    private LocalDate birthDate;
}