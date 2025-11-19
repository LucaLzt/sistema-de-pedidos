package com.proyectos.sistemadepedidos.auth.application.port.in;

public record RegisterUserCommand(
        String name,
        String lastName,
        String email,
        String rawPassword
) {}
