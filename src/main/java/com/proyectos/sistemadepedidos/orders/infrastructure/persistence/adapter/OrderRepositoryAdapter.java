package com.proyectos.sistemadepedidos.orders.infrastructure.persistence.adapter;

import com.proyectos.sistemadepedidos.orders.domain.model.Order;
import com.proyectos.sistemadepedidos.orders.domain.model.OrderItem;
import com.proyectos.sistemadepedidos.orders.domain.repository.OrderRepository;
import com.proyectos.sistemadepedidos.orders.infrastructure.persistence.SpringDataOrderRepository;
import com.proyectos.sistemadepedidos.orders.infrastructure.persistence.entity.OrderItemJpaEntity;
import com.proyectos.sistemadepedidos.orders.infrastructure.persistence.entity.OrderJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderRepository springDataOrderRepository;


    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = toEntity(order);
        OrderJpaEntity saved = springDataOrderRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return springDataOrderRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return springDataOrderRepository.findByUserId(userId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        springDataOrderRepository.deleteById(id);
    }

    private OrderJpaEntity toEntity(Order order) {
        OrderJpaEntity entity = OrderJpaEntity.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();

        // Mapear items
        List<OrderItemJpaEntity> itemEntities = order.getItems().stream()
                .map(item -> OrderItemJpaEntity.builder()
                        .id(item.getId())
                        .order(entity)
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subtotal(item.getSubtotal())
                        .build())
                .toList();

        entity.setItems(itemEntities);
        return entity;
    }

    private Order toDomain(OrderJpaEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
                .map(ie -> new OrderItem(
                        ie.getId(),
                        ie.getProductId(),
                        ie.getProductName(),
                        ie.getQuantity(),
                        ie.getUnitPrice()
                ))
                .toList();

        return new Order(
                entity.getId(),
                entity.getUserId(),
                entity.getStatus(),
                entity.getTotalAmount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                items
        );
    }

}
