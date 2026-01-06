package com.lucas.login_auth_api.services;

import com.lucas.login_auth_api.domain.entities.User;
import com.lucas.login_auth_api.dto.AuthResponseDTO;
import com.lucas.login_auth_api.dto.LoginRequestDTO;
import com.lucas.login_auth_api.dto.RegisterRequestDTO;
import com.lucas.login_auth_api.exceptions.EmailAlreadyRegisteredException;
import com.lucas.login_auth_api.exceptions.InvalidCredentialsException;
import com.lucas.login_auth_api.exceptions.UserNotFoundException;
import com.lucas.login_auth_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthResponseDTO login(LoginRequestDTO body) {
        User user = repository.findByEmail(body.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = tokenService.generateToken(user);
        return new AuthResponseDTO(user.getName(), token);
    }

    public AuthResponseDTO register(RegisterRequestDTO body) {
        if (repository.findByEmail(body.email()).isPresent()) {
            throw new EmailAlreadyRegisteredException("Email already registered");
        }

        User newUser = new User();
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setPassword(passwordEncoder.encode(body.password()));

        repository.save(newUser);

        String token = tokenService.generateToken(newUser);
        return new AuthResponseDTO(newUser.getName(), token);
    }
}
