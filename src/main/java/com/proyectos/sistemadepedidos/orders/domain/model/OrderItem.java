package com.proyectos.sistemadepedidos.orders.domain.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderItem {

    private final Long id;
    private final Long productId;
    private final String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    public OrderItem(Long id, Long productId, String productName, int quantity, BigDecimal unitPrice) {

        if (productId == null) {
            throw new IllegalArgumentException("productId cannot be null.");
        }
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("productName cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero.");
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("unitPrice must be greater than zero. ");
        }

        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public void changeQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero.");
        }
        this.quantity = newQuantity;
        recalculateSubtotal();
    }

    public void changeUnitPrice(BigDecimal newUnitPrice) {
        if (newUnitPrice == null || newUnitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("unitPrice must be greater than zero.");
        }
        this.unitPrice = newUnitPrice;
        recalculateSubtotal();
    }

    public void recalculateSubtotal() {
        this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

}
