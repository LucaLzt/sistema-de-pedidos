package com.proyectos.sistemadepedidos.notifications.infrastructure.persistence;

import com.proyectos.sistemadepedidos.notifications.domain.model.NotificationStatus;
import com.proyectos.sistemadepedidos.notifications.infrastructure.persistence.entity.EmailNotificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataEmailNotificationRepository extends JpaRepository<EmailNotificationJpaEntity, Long> {

    List<EmailNotificationJpaEntity> findByStatus(NotificationStatus status);

}
