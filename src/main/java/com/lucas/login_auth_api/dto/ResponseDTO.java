package com.lucas.login_auth_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "AuthResponse",
        description = "Resposta retornada após autenticação ou cadastro bem-sucedido"
)
public record ResponseDTO(

        @Schema(
                description = "Nome do usuário autenticado",
                example = "Lucas Henrique"
        )
        String name,

        @Schema(
                description = "Token JWT para autenticação nas próximas requisições",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        )
        String token

) {}
