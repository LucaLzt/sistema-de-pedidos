package com.proyectos.sistemadepedidos.shared.domain.exception;

public abstract class ConflictException extends DomainException {
    protected ConflictException(String message) {
        super(message);
    }
}
