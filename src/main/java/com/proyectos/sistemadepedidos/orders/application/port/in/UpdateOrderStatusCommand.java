package com.proyectos.sistemadepedidos.orders.application.port.in;

import com.proyectos.sistemadepedidos.orders.domain.model.OrderStatus;

public record UpdateOrderStatusCommand(
        Long orderId,
        String userEmail,
        OrderStatus newStatus
) {
}
