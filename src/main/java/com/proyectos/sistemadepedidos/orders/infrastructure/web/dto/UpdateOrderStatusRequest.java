package com.proyectos.sistemadepedidos.orders.infrastructure.web.dto;

public record UpdateOrderStatusRequest(
        String newStatus    // "PAID", "CANCELLED", "SHIPPED"
) {
}
