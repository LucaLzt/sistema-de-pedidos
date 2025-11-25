package com.proyectos.sistemadepedidos.orders.application.service;

import com.proyectos.sistemadepedidos.notifications.application.in.OrderEmailCommand;
import com.proyectos.sistemadepedidos.notifications.application.in.SendOrderEmailUseCase;
import com.proyectos.sistemadepedidos.orders.application.port.in.OrderResult;
import com.proyectos.sistemadepedidos.orders.application.port.in.PlaceOrderCommand;
import com.proyectos.sistemadepedidos.orders.application.port.in.PlaceOrderUseCase;
import com.proyectos.sistemadepedidos.orders.domain.model.Order;
import com.proyectos.sistemadepedidos.orders.domain.model.OrderItem;
import com.proyectos.sistemadepedidos.orders.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceOrderService implements PlaceOrderUseCase {

    @Value(value = "${frontend.order-details.base-url}")
    private String frontendUrl;

    private final OrderRepository orderRepository;
    private final SendOrderEmailUseCase sendOrderEmailUseCase;

    @Override
    public OrderResult placeOrder(PlaceOrderCommand command) {

        Order order = Order.newOrder(command.userId());

        for (PlaceOrderCommand.OrderItemCommand item : command.items()) {
            OrderItem orderItem = new OrderItem(
                    null,
                    item.productId(),
                    item.productName(),
                    item.quantity(),
                    new BigDecimal(item.unitPrice())
            );
            order.addItem(orderItem);
        }

        Order saved = orderRepository.save(order);

        OrderEmailCommand emailCommand = new OrderEmailCommand(
                command.userEmail(),
                saved.getId(),
                saved.getStatus().name(),
                frontendUrl + saved.getId()
        );

        sendOrderEmailUseCase.sendOrderEmail(emailCommand);

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
                        .collect(Collectors.toList())
        );
    }
}
