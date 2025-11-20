package com.proyectos.sistemadepedidos.auth.domain.repository;

import com.proyectos.sistemadepedidos.auth.domain.model.RefreshToken;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByUserId(Long userId);

    void deleteById(Long id);

    void deleteAllByUserId(Long userId);

}
