package com.proyectos.sistemadepedidos.shared.domain.exception;

public abstract class UnauthorizedException extends DomainException {
    protected UnauthorizedException(String message) {
        super(message);
    }
}
