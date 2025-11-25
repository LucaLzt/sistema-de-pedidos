package com.proyectos.sistemadepedidos.auth.domain.exception;

import com.proyectos.sistemadepedidos.shared.domain.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
