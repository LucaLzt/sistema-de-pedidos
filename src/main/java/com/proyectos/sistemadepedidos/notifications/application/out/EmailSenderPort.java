package com.proyectos.sistemadepedidos.notifications.application.out;

import com.proyectos.sistemadepedidos.notifications.domain.model.EmailNotification;

public interface EmailSenderPort {

    void sendResetPasswordEmail(EmailNotification notification);

    void sendOrderEmail(EmailNotification notification);

}
