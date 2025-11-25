package com.proyectos.sistemadepedidos.orders.infrastructure.persistence;

import com.proyectos.sistemadepedidos.orders.infrastructure.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity, Long> {

    List<OrderJpaEntity> findByUserId(Long userId);

}
