package com.proyectos.sistemadepedidos.orders.infrastructure.web.dto;

import com.proyectos.sistemadepedidos.orders.application.port.in.OrderResult;

import java.time.Instant;
import java.util.List;

public record OrderResponse(
        Long id,
        Long userId,
        String status,
        String totalAmount,
        Instant createdAt,
        Instant updatedAt,
        List<ItemResponse> items
) {

    public static OrderResponse fromResult(OrderResult result) {
        List<ItemResponse> itemResponses = result.items().stream()
                .map(i -> new ItemResponse(
                        i.productId(),
                        i.productName(),
                        i.quantity(),
                        i.unitPrice().toPlainString(),
                        i.subtotal().toPlainString()
                ))
                .toList();

        return new OrderResponse(
                result.id(),
                result.userId(),
                result.status().name(),
                result.totalAmount().toPlainString(),
                result.createdAt(),
                result.updatedAt(),
                itemResponses
        );
    }
}

