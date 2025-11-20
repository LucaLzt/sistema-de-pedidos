package com.proyectos.sistemadepedidos.notifications.domain.repository;

import com.proyectos.sistemadepedidos.notifications.domain.model.EmailNotification;

public interface EmailNotificationRepository {

    EmailNotification save(EmailNotification notification);

}
