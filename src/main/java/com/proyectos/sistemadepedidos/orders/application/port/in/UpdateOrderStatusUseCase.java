package com.proyectos.sistemadepedidos.orders.application.port.in;

public interface UpdateOrderStatusUseCase {

    OrderResult updateStatus(UpdateOrderStatusCommand command);

}
