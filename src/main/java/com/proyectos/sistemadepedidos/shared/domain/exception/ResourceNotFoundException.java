package com.proyectos.sistemadepedidos.shared.domain.exception;

public abstract class ResourceNotFoundException extends DomainException {
    protected ResourceNotFoundException(String message) {
        super(message);
    }
}
