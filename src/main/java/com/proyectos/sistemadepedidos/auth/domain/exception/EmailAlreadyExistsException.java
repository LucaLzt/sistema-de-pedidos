package com.proyectos.sistemadepedidos.auth.domain.exception;

import com.proyectos.sistemadepedidos.shared.domain.exception.ConflictException;

public class EmailAlreadyExistsException extends ConflictException {
    public EmailAlreadyExistsException(String email) {
        super("The email address is already in use: " + email);
    }
}
