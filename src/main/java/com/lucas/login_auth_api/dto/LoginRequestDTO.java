package com.lucas.login_auth_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "LoginRequest",
        description = "DTO utilizado para autenticação do usuário"
)
public record LoginRequestDTO(

        @Schema(
                description = "Email do usuário cadastrado",
                example = "usuario@email.com"
        )
        @Email(message = "Email inválido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @Schema(
                description = "Senha do usuário",
                example = "123456"
        )
        @NotBlank(message = "Senha é obrigatória")
        String password

) {}
