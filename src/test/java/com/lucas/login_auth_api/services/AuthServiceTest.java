package com.lucas.login_auth_api.services;

import com.lucas.login_auth_api.domain.entities.Role;
import com.lucas.login_auth_api.domain.entities.User;
import com.lucas.login_auth_api.dto.AuthResponseDTO;
import com.lucas.login_auth_api.dto.LoginRequestDTO;
import com.lucas.login_auth_api.dto.RegisterRequestDTO;
import com.lucas.login_auth_api.exceptions.EmailAlreadyRegisteredException;
import com.lucas.login_auth_api.exceptions.InvalidCredentialsException;
import com.lucas.login_auth_api.exceptions.UserNotFoundException;
import com.lucas.login_auth_api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("Lucas");
        user.setEmail("lucas@email.com");
        user.setPassword("Senha@123");
        user.setRole(Role.ROLE_USER);
    }

    // ========== LOGIN TESTS ==========
    @Test
    @DisplayName("Login deve retornar token quando credenciais forem válidas")
    void loginSucesso() {
        LoginRequestDTO request = new LoginRequestDTO("lucas@email.com", "123456");

        when(repository.findByEmail(request.email()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(request.password(), user.getPassword()))
                .thenReturn(true);

        when(tokenService.generateToken(user))
                .thenReturn("jwt-token-aqui");

        AuthResponseDTO response = authService.login(request);

        assertNotNull(response);
        assertEquals("Lucas", response.name());
        assertEquals("jwt-token-aqui", response.token());
    }

    @Test
    @DisplayName("Login deve lançar UserNotFoundException quando email não existir")
    void loginUsuarioNaoEncontrado() {
        LoginRequestDTO request = new LoginRequestDTO("notfound@email.com", "123");

        when(repository.findByEmail(request.email()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authService.login(request));
    }

    @Test
    @DisplayName("Login deve lançar InvalidCredentialsException quando senha estiver errada")
    void loginSenhaIncorreta() {
        LoginRequestDTO request = new LoginRequestDTO("lucas@email.com", "wrong");

        when(repository.findByEmail(request.email()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(request.password(), user.getPassword()))
                .thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> authService.login(request));
    }

    // ========== REGISTER TESTS ===========
    @Test
    @DisplayName("Register deve criar novo usuário com sucesso")
    void registerSucesso() {

        RegisterRequestDTO request = new RegisterRequestDTO(
                "Lucas",
                "lucas@email.com",
                "12345678"
        );

        when(repository.findByEmail(request.email()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(request.password()))
                .thenReturn("encodedPass");

        when(tokenService.generateToken(any(User.class)))
                .thenReturn("jwt-token");

        AuthResponseDTO response = authService.register(request);

        assertNotNull(response);
        assertEquals("Lucas", response.name());
        assertEquals("jwt-token", response.token());

        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Register deve lançar EmailAlreadyRegisteredException quando email já existir")
    void registerEmailExistente() {

        RegisterRequestDTO request = new RegisterRequestDTO(
                "Lucas",
                "lucas@email.com",
                "12345678"
        );

        when(repository.findByEmail(request.email()))
                .thenReturn(Optional.of(user));

        assertThrows(EmailAlreadyRegisteredException.class, () -> authService.register(request));
    }
}
