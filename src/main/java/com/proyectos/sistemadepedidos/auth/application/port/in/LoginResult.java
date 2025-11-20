package com.proyectos.sistemadepedidos.auth.application.port.in;

public record LoginResult(
        String accessToken,
        String refreshToken
) {}
