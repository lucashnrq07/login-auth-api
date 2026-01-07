package com.lucas.login_auth_api.services;

import com.lucas.login_auth_api.domain.entities.User;
import com.lucas.login_auth_api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("Lucas");
        user.setEmail("lucas@email.com");
        user.setPassword("Senha@123");
    }

    @Test
    @DisplayName("loadUserByUsername deve retornar UserDetails quando o usuário existe")
    void loadUserByUsernameSuccess() {

        when(repository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        UserDetails details = customUserDetailsService.loadUserByUsername(user.getEmail());

        assertNotNull(details);
        assertEquals(user.getEmail(), details.getUsername());
        assertEquals(user.getPassword(), details.getPassword());

        // sem authorities
        assertTrue(details.getAuthorities().isEmpty());
    }

    @Test
    @DisplayName("loadUserByUsername deve lançar UsernameNotFoundException quando o usuário não existe")
    void loadUserByUsernameNotFound() {

        when(repository.findByEmail(user.getEmail()))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(user.getEmail())
        );
    }
}
