package com.proyectos.sistemadepedidos.auth.domain.repository;

import com.proyectos.sistemadepedidos.auth.domain.model.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetRepository {

    PasswordResetToken save(PasswordResetToken token);
    Optional<PasswordResetToken> findByToken(String token);
    void deleteById(Long id);
    void deleteExpiredTokens();
    void deleteByUserId(Long userId);

}
