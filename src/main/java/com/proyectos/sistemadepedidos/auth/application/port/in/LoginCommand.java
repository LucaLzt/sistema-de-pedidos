package com.proyectos.sistemadepedidos.auth.application.port.in;

public record LoginCommand(
        String email,
        String rawPassword
) {}
