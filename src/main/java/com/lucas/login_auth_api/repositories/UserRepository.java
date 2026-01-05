package com.lucas.login_auth_api.repositories;

import com.lucas.login_auth_api.domain.entities.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Id> {
    Optional<User> findByEmail(String email);
}
