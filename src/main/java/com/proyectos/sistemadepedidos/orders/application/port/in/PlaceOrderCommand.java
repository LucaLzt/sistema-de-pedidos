package com.proyectos.sistemadepedidos.orders.application.port.in;

import java.util.List;

public record PlaceOrderCommand(
        Long userId,
        String userEmail,
        List<OrderItemCommand> items
) {

    public record OrderItemCommand(
            Long productId,
            String productName,
            int quantity,
            String unitPrice
    ) {
    }

}
