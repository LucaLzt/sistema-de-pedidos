package com.proyectos.sistemadepedidos.shared.domain.exception;

public abstract class BadRequestException extends DomainException {
    protected BadRequestException(String message) {
        super(message);
    }
}
