package com.proyectos.sistemadepedidos.notifications.application.service;

import com.proyectos.sistemadepedidos.notifications.application.in.OrderEmailCommand;
import com.proyectos.sistemadepedidos.notifications.application.in.SendOrderEmailUseCase;
import com.proyectos.sistemadepedidos.notifications.application.out.EmailSenderPort;
import com.proyectos.sistemadepedidos.notifications.domain.model.EmailNotification;
import com.proyectos.sistemadepedidos.notifications.domain.model.EmailType;
import com.proyectos.sistemadepedidos.notifications.domain.model.NotificationStatus;
import com.proyectos.sistemadepedidos.notifications.domain.repository.EmailNotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class SendOrderEmailService implements SendOrderEmailUseCase {

    private final EmailNotificationRepository emailNotificationRepository;
    private final EmailSenderPort emailSenderPort;

    @Override
    public void sendOrderEmail(OrderEmailCommand command) {
        String payload = """
                    "orderId": %d,
                    "status": "%s",
                    "detailsUrl": "%s"
                """.formatted(
                command.orderId(),
                command.status(),
                command.detailUrl()
        );

        String subject = "Order Update #" + command.orderId();
        String body = "Your order has a new status: " + command.status();

        EmailNotification notification = new EmailNotification(
                null,
                command.email(),
                subject,
                body,
                EmailType.ORDER_STATUS_UPDATE,
                NotificationStatus.PENDING,
                Instant.now(),
                null,
                null,
                command.orderId(),
                payload
        );

        EmailNotification saved = emailNotificationRepository.save(notification);
        emailSenderPort.sendOrderEmail(saved);
    }

}
