package com.proyectos.sistemadepedidos.auth.application.service;

import com.proyectos.sistemadepedidos.auth.application.port.in.RefreshTokenUseCase;
import com.proyectos.sistemadepedidos.auth.application.port.out.TokenProviderPort;
import com.proyectos.sistemadepedidos.auth.domain.model.RefreshToken;
import com.proyectos.sistemadepedidos.auth.domain.model.User;
import com.proyectos.sistemadepedidos.auth.domain.repository.RefreshTokenRepository;
import com.proyectos.sistemadepedidos.auth.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService implements RefreshTokenUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final TokenProviderPort tokenProviderPort;

    @Override
    public String refresh(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (refreshToken.isRevoked() || refreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Refresh token has expired");
        }

        User user = userRepository.findById(refreshToken.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return tokenProviderPort.generateAccessToken(user);
    }

}
