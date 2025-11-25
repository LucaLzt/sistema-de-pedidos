package com.proyectos.sistemadepedidos.auth.domain.exception;

import com.proyectos.sistemadepedidos.shared.domain.exception.UnauthorizedException;

public class InvalidCredentialsException extends UnauthorizedException {
    public InvalidCredentialsException() {
        super("Invalid email or password.");
    }
}
