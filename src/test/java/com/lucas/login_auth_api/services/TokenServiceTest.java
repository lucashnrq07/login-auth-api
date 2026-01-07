package com.lucas.login_auth_api.services;

import com.lucas.login_auth_api.domain.entities.User;
import com.lucas.login_auth_api.exceptions.InvalidTokenException;
import com.lucas.login_auth_api.domain.entities.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    private TokenService tokenService;

    private User user;

    @BeforeEach
    void setup() {
        tokenService = new TokenService();

        // define o valor do @Value manualmente nos testes
        ReflectionTestUtils.setField(tokenService, "secret", "my-secret-key");

        user = new User();
        user.setEmail("teste@email.com");
        user.setRole(Role.ROLE_USER);
    }

    @Test
    @DisplayName("generateToken deve retornar um token válido não nulo")
    void generateTokenShouldReturnValidToken() {
        String token = tokenService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    @DisplayName("validateToken deve retornar o email do usuário quando o token é válido")
    void validateTokenShouldReturnUserEmail() {
        String token = tokenService.generateToken(user);

        String subject = tokenService.validateToken(token);

        assertEquals(user.getEmail(), subject);
    }

    @Test
    @DisplayName("validateToken deve lançar InvalidTokenException ao receber token inválido")
    void validateTokenShouldThrowExceptionForInvalidToken() {
        String invalidToken = "token-invalido";

        assertThrows(
                InvalidTokenException.class,
                () -> tokenService.validateToken(invalidToken)
        );
    }
}
