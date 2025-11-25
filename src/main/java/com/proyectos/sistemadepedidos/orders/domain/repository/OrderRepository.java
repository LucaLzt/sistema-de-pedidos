package com.proyectos.sistemadepedidos.orders.domain.repository;

import com.proyectos.sistemadepedidos.orders.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByUserId(Long userId);

    void deleteById(Long id);

}
