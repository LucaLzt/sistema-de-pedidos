package com.proyectos.sistemadepedidos.shared.domain.exception;

public abstract class InternalServerErrorException extends DomainException {
    protected InternalServerErrorException(String message, Throwable cause) {
        super(message);
        this.initCause(cause);
    }
}
