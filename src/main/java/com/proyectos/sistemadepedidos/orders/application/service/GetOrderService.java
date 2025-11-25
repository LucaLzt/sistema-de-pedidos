package com.proyectos.sistemadepedidos.orders.application.service;

import com.proyectos.sistemadepedidos.orders.application.port.in.GetOrderUseCase;
import com.proyectos.sistemadepedidos.orders.application.port.in.OrderResult;
import com.proyectos.sistemadepedidos.orders.domain.model.Order;
import com.proyectos.sistemadepedidos.orders.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetOrderService implements GetOrderUseCase {

    private final OrderRepository orderRepository;

    @Override
    public OrderResult getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));
        return toResult(order);
    }

    private OrderResult toResult(Order order) {
        return new OrderResult(
                order.getId(),
                order.getUserId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getItems()
                        .stream()
                        .map(i -> new OrderResult.Item(
                                i.getProductId(),
                                i.getProductName(),
                                i.getQuantity(),
                                i.getUnitPrice(),
                                i.getSubtotal()
                        ))
                        .toList()
        );
    }
}
