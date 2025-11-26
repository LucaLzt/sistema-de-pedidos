package com.proyectos.sistemadepedidos.orders.domain.exception;

import com.proyectos.sistemadepedidos.shared.domain.exception.BadRequestException;

public class InvalidOrderStatusException extends BadRequestException {
    public InvalidOrderStatusException(String message) {
        super(message);
    }
}
