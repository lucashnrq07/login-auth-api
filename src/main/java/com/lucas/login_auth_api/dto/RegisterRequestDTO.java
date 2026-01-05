package com.lucas.login_auth_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "RegisterRequest",
        description = "DTO utilizado para cadastro de novos usuários"
)
public record RegisterRequestDTO(

        @Schema(
                description = "Nome completo do usuário",
                example = "Lucas Henrique"
        )
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @Schema(
                description = "Email válido do usuário",
                example = "lucas@email.com"
        )
        @Email(message = "Email inválido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @Schema(
                description = "Senha do usuário",
                example = "Senha@123",
                accessMode = Schema.AccessMode.WRITE_ONLY
        )
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
        String password

) {}
