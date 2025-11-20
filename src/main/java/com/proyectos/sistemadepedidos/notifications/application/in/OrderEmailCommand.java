package com.proyectos.sistemadepedidos.notifications.application.in;

public record OrderEmailCommand(
        String email,
        Long orderId,
        String status,
        String detailUrl
) {
}
