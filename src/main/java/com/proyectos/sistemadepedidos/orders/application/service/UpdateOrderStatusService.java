package com.proyectos.sistemadepedidos.orders.application.service;

import com.proyectos.sistemadepedidos.notifications.application.in.OrderEmailCommand;
import com.proyectos.sistemadepedidos.notifications.application.in.SendOrderEmailUseCase;
import com.proyectos.sistemadepedidos.orders.application.port.in.OrderResult;
import com.proyectos.sistemadepedidos.orders.application.port.in.UpdateOrderStatusCommand;
import com.proyectos.sistemadepedidos.orders.application.port.in.UpdateOrderStatusUseCase;
import com.proyectos.sistemadepedidos.orders.domain.exception.InvalidOrderStatusException;
import com.proyectos.sistemadepedidos.orders.domain.exception.OrderNotFoundException;
import com.proyectos.sistemadepedidos.orders.domain.model.Order;
import com.proyectos.sistemadepedidos.orders.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateOrderStatusService implements UpdateOrderStatusUseCase {

    @Value(value = "${frontend.order-details.base-url}")
    private String frontendUrl;

    private final OrderRepository orderRepository;
    private final SendOrderEmailUseCase sendOrderEmailUseCase;

    @Override
    public OrderResult updateStatus(UpdateOrderStatusCommand command) {

        Order order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new OrderNotFoundException(command.orderId()));

        switch (command.newStatus()) {
            case PAID -> order.markAsPaid();
            case CANCELLED -> order.cancel();
            case SHIPPED -> order.markAsShipped();
            default -> throw new InvalidOrderStatusException("Unexpected status: " + command.newStatus());
        }

        Order saved = orderRepository.save(order);

        sendOrderEmailUseCase.sendOrderEmail(
                new OrderEmailCommand(
                        command.userEmail(),
                        saved.getId(),
                        saved.getStatus().name(),
                        frontendUrl + saved.getId()
                )
        );

        return toResult(saved);
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
