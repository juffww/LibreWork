package com.librework.modules.identity.domain.repository;

import com.librework.modules.identity.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
    User save(User user);
}
