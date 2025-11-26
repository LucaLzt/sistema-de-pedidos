package com.proyectos.sistemadepedidos.notifications.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectos.sistemadepedidos.notifications.application.in.OrderEmailCommand;
import com.proyectos.sistemadepedidos.notifications.application.in.SendOrderEmailUseCase;
import com.proyectos.sistemadepedidos.notifications.application.out.EmailSenderPort;
import com.proyectos.sistemadepedidos.notifications.domain.exception.NotificationProcessingException;
import com.proyectos.sistemadepedidos.notifications.domain.model.EmailNotification;
import com.proyectos.sistemadepedidos.notifications.domain.repository.EmailNotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class SendOrderEmailService implements SendOrderEmailUseCase {

    private final EmailNotificationRepository emailNotificationRepository;
    private final EmailSenderPort emailSenderPort;
    private final ObjectMapper objectMapper;

    @Override
    public void sendOrderEmail(OrderEmailCommand command) {

        String payloadJson;
        try {
            Map<String, Object> payloadData = Map.of(
                    "orderId", command.orderId(),
                    "status", command.status(),
                    "detailUrl", command.detailUrl()
            );
            payloadJson = objectMapper.writeValueAsString(payloadData);
        } catch (JsonProcessingException e) {
            throw new NotificationProcessingException("Failed to serialize order email payload", e);
        }

        String subject = "Order Update #" + command.orderId();
        String body = "Your order has a new status: " + command.status();

        EmailNotification notification = EmailNotification.createOrderStatusUpdate(
                command.email(),
                subject,
                body,
                command.orderId(),
                payloadJson
        );

        EmailNotification saved = emailNotificationRepository.save(notification);
        emailSenderPort.sendOrderEmail(saved);
    }

}
