package com.lucas.login_auth_api.controllers;

import com.lucas.login_auth_api.dto.AuthResponseDTO;
import com.lucas.login_auth_api.dto.LoginRequestDTO;
import com.lucas.login_auth_api.dto.RegisterRequestDTO;
import com.lucas.login_auth_api.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Autenticação",
        description = "Endpoints responsáveis por login e cadastro de usuários"
)
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Login do usuário",
            description = "Autentica o usuário com email e senha e retorna um token JWT"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário autenticado com sucesso",
                    content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO body
    ) {
        return ResponseEntity.ok(authService.login(body));
    }

    @Operation(
            summary = "Cadastro de usuário",
            description = "Cria um novo usuário e retorna um token JWT automaticamente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))
            ),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @RequestBody @Valid RegisterRequestDTO body
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(body));
    }
}
