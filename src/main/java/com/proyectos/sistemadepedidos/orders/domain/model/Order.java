package com.proyectos.sistemadepedidos.orders.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Order {

    private final Long id;
    private final Long userId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private final Instant createdAt;
    private Instant updatedAt;
    private final List<OrderItem> items;

    public Order(Long id, Long userId, OrderStatus status, BigDecimal totalAmount, Instant createdAt, Instant updatedAt, List<OrderItem> items) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null.");
        }
        if (status == null) {
            throw new IllegalArgumentException("status cannot be null.");
        }

        this.id = id;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt != null ? createdAt : Instant.now();
        this.updatedAt = updatedAt != null ? updatedAt : this.createdAt;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        this.totalAmount = totalAmount != null ? totalAmount : calculateTotal();
    }

    public static Order newOrder(Long userId) {
        Instant now = Instant.now();
        return new Order(
                null,
                userId,
                OrderStatus.CREATED,
                BigDecimal.ZERO,
                now,
                now,
                new ArrayList<>()
        );
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        this.items.add(item);
        recalculateTotal();
    }

    public void removeItemByProductId(Long productId) {
        if (productId == null) {
            return;
        }
        this.items.removeIf(i -> i.getProductId().equals(productId));
        recalculateTotal();
    }

    public void clearItems() {
        this.items.clear();
        this.totalAmount = BigDecimal.ZERO;
        touch();
    }

    public void markAsPaid() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Only CREATED orders can be marked as PAID");
        }
        this.status = OrderStatus.PAID;
        touch();
    }

    public void cancel() {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel an order that is already SHIPPED");
        }
        this.status = OrderStatus.CANCELLED;
        touch();
    }

    public void markAsShipped() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("Only PAID orders can be marked as SHIPPED");
        }
        this.status = OrderStatus.SHIPPED;
        touch();
    }

    public void recalculateTotal() {
        this.totalAmount = calculateTotal();
        touch();
    }

    private BigDecimal calculateTotal() {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

}
