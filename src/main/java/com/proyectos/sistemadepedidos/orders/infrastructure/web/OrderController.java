package com.proyectos.sistemadepedidos.orders.infrastructure.web;

import com.proyectos.sistemadepedidos.auth.infrastructure.security.SecurityUser;
import com.proyectos.sistemadepedidos.orders.application.port.in.*;
import com.proyectos.sistemadepedidos.orders.domain.model.OrderStatus;
import com.proyectos.sistemadepedidos.orders.infrastructure.web.dto.OrderResponse;
import com.proyectos.sistemadepedidos.orders.infrastructure.web.dto.PlaceOrderRequest;
import com.proyectos.sistemadepedidos.orders.infrastructure.web.dto.UpdateOrderStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final GetOrderByUserUseCase getOrderByUserUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@AuthenticationPrincipal SecurityUser principal, @RequestBody PlaceOrderRequest request) {
        Long userId = principal.getDomainUser().getId();
        String userEmail = principal.getDomainUser().getEmail();

        PlaceOrderCommand command = new PlaceOrderCommand(
                userId,
                userEmail,
                request.items().stream()
                        .map(it -> new PlaceOrderCommand.OrderItemCommand(
                                it.productId(),
                                it.productName(),
                                it.quantity(),
                                it.unitPrice()
                        ))
                        .toList()
        );

        OrderResult result = placeOrderUseCase.placeOrder(command);

        return ResponseEntity.ok(OrderResponse.fromResult(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@AuthenticationPrincipal SecurityUser principal, @PathVariable Long id) {
        OrderResult result = getOrderUseCase.getById(id);

        if (!result.userId().equals(principal.getDomainUser().getId())) {
            throw new AccessDeniedException("You do not have permission to access this order.");
        }

        return ResponseEntity.ok(OrderResponse.fromResult(result));
    }

    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders(@AuthenticationPrincipal SecurityUser principal) {
        Long userId = principal.getDomainUser().getId();

        List<OrderResult> results = getOrderByUserUseCase.getByUserId(userId);

        List<OrderResponse> responses = results.stream()
                .map(OrderResponse::fromResult)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(@AuthenticationPrincipal SecurityUser principal, @PathVariable Long id, @RequestBody UpdateOrderStatusRequest request) {
        String userEmail = principal.getDomainUser().getEmail();
        OrderStatus newStatus = OrderStatus.valueOf(request.newStatus());

        UpdateOrderStatusCommand command = new UpdateOrderStatusCommand(
                id,
                userEmail,
                newStatus
        );

        OrderResult result = updateOrderStatusUseCase.updateStatus(command);

        return ResponseEntity.ok(OrderResponse.fromResult(result));
    }

}
