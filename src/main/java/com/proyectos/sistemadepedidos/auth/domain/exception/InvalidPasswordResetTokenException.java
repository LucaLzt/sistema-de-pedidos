package com.proyectos.sistemadepedidos.auth.domain.exception;

import com.proyectos.sistemadepedidos.shared.domain.exception.UnauthorizedException;

public class InvalidPasswordResetTokenException extends UnauthorizedException {
    public InvalidPasswordResetTokenException(String message) {
        super(message);
    }
}
