package com.proyectos.sistemadepedidos.auth.domain.exception;

import com.proyectos.sistemadepedidos.shared.domain.exception.UnauthorizedException;

public class InvalidRefreshTokenException extends UnauthorizedException {
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}
