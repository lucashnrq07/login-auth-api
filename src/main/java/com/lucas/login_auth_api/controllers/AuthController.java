package com.lucas.login_auth_api.controllers;

import com.lucas.login_auth_api.domain.entities.User;
import com.lucas.login_auth_api.dto.LoginRequestDTO;
import com.lucas.login_auth_api.dto.RegisterRequestDTO;
import com.lucas.login_auth_api.dto.ResponseDTO;
import com.lucas.login_auth_api.repositories.UserRepository;
import com.lucas.login_auth_api.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de login e cadastro de usuários")
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Operation(
            summary = "Login do usuário",
            description = "Autentica o usuário com email e senha e retorna um token JWT"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário autenticado com sucesso",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Credenciais inválidas"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado"
            )
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO body
    ) {
        User user = repository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    @Operation(
            summary = "Cadastro de usuário",
            description = "Cria um novo usuário e retorna um token JWT automaticamente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Email já cadastrado ou dados inválidos"
            )
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(
            @RequestBody @Valid RegisterRequestDTO body
    ) {
        Optional<User> user = repository.findByEmail(body.email());

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setName(body.name());
            newUser.setEmail(body.email());
            newUser.setPassword(passwordEncoder.encode(body.password()));

            repository.save(newUser);

            String token = tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }
}
