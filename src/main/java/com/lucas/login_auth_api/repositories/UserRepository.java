package com.lucas.login_auth_api.repositories;

import com.lucas.login_auth_api.domain.entities.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Id> {
}
