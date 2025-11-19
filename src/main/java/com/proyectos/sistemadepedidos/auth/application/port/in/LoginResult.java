package com.proyectos.sistemadepedidos.auth.application.port.in;

public record LoginResult(
        String accesToken,
        String refreshToken
) {}
