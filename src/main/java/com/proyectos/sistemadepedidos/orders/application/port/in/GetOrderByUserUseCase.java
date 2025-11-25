package com.proyectos.sistemadepedidos.orders.application.port.in;

import java.util.List;

public interface GetOrderByUserUseCase {

    List<OrderResult> getByUserId(Long userId);

}
