package com.proyectos.sistemadepedidos.orders.domain.exception;

import com.proyectos.sistemadepedidos.shared.domain.exception.ResourceNotFoundException;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(Long id) {
        super("Order not found with id: " + id);
    }
}
