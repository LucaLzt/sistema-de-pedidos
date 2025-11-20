package com.proyectos.sistemadepedidos.notifications.infrastructure.persistence.adapter;

import com.proyectos.sistemadepedidos.notifications.domain.model.EmailNotification;
import com.proyectos.sistemadepedidos.notifications.domain.repository.EmailNotificationRepository;
import com.proyectos.sistemadepedidos.notifications.infrastructure.persistence.SpringDataEmailNotificationRepository;
import com.proyectos.sistemadepedidos.notifications.infrastructure.persistence.entity.EmailNotificationJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationRepositoryAdapter implements EmailNotificationRepository {

    private final SpringDataEmailNotificationRepository springDataEmailNotificationRepository;

    @Override
    public EmailNotification save(EmailNotification notification) {
        EmailNotificationJpaEntity entity = toEntity(notification);
        EmailNotificationJpaEntity saved = springDataEmailNotificationRepository.save(entity);
        return toDomain(saved);
    }

    private EmailNotificationJpaEntity toEntity(EmailNotification notification) {
        return EmailNotificationJpaEntity.builder()
                .id(notification.getId())
                .type(notification.getType())
                .toAddress(notification.getToAddress())
                .subject(notification.getSubject())
                .body(notification.getBody())
                .status(notification.getStatus())
                .createdAt(notification.getCreatedAt())
                .sentAt(notification.getSentAt())
                .errorMessage(notification.getErrorMessage())
                .relatedOrderId(notification.getRelatedOrderId())
                .payloadJson(notification.getPayloadJson())
                .build();
    }

    private EmailNotification toDomain(EmailNotificationJpaEntity entity) {
        return EmailNotification.restore(
                entity.getId(),
                entity.getToAddress(),
                entity.getSubject(),
                entity.getBody(),
                entity.getType(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getSentAt(),
                entity.getErrorMessage(),
                entity.getRelatedOrderId(),
                entity.getPayloadJson()
        );
    }
}
