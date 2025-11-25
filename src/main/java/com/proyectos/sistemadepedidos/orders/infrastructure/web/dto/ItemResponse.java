package com.proyectos.sistemadepedidos.orders.infrastructure.web.dto;

public record ItemResponse(
        Long productId,
        String productName,
        int quantity,
        String unitPrice,
        String subtotal
) {
}
