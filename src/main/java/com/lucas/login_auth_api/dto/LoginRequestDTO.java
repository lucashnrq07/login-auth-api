package com.lucas.login_auth_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "LoginRequest",
        description = "DTO utilizado para autenticação do usuário"
)
public record LoginRequestDTO(

        @Schema(
                description = "Email do usuário cadastrado",
                example = "lucas@email.com"
        )
        @Email(message = "Email inválido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @Schema(
                description = "Senha do usuário",
                example = "Senha@123"
        )
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
        String password

) {}
