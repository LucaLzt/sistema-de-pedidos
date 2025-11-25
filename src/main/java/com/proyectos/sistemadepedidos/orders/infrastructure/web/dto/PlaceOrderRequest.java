package com.proyectos.sistemadepedidos.orders.infrastructure.web.dto;

import java.util.List;

public record PlaceOrderRequest(
        List<Item> items
) {

    public record Item(
            Long productId,
            String productName,
            int quantity,
            String unitPrice
    ) {
    }

}
