package com.proyectos.sistemadepedidos.orders.application.port.in;

public interface PlaceOrderUseCase {

    OrderResult placeOrder(PlaceOrderCommand command);

}
