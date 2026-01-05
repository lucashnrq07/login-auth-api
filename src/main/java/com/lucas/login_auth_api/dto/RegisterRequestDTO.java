package com.lucas.login_auth_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO (
        @NotBlank
        String name,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password
){}