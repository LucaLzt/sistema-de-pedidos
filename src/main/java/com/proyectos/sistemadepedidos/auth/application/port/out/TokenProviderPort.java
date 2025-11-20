package com.proyectos.sistemadepedidos.auth.application.port.out;

import com.proyectos.sistemadepedidos.auth.domain.model.User;

import java.time.Duration;

public interface TokenProviderPort {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    Duration getRefreshTokenTtl();

    String generatePasswordResetToken(User user);

    boolean isAccessTokenValid(String token);

    String getUsernameFromAccessToken(String token);

}
