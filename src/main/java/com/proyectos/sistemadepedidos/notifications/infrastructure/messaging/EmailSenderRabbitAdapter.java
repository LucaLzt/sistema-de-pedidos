package com.proyectos.sistemadepedidos.notifications.infrastructure.messaging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectos.sistemadepedidos.notifications.application.out.EmailSenderPort;
import com.proyectos.sistemadepedidos.notifications.domain.model.EmailNotification;
import com.proyectos.sistemadepedidos.notifications.domain.model.EmailType;
import com.proyectos.sistemadepedidos.notifications.infrastructure.messaging.dto.OrderEmailDTO;
import com.proyectos.sistemadepedidos.notifications.infrastructure.messaging.dto.RecoveryEmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSenderRabbitAdapter implements EmailSenderPort {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${rabbitmq.email.exchange}")
    private String emailExchange;

    @Value("${rabbitmq.email.recovery-password.routing-key}")
    private String recoveryRoutingKey;

    @Value("${rabbitmq.email.order.routing-key}")
    private String orderRoutingKey;

    // URLs base del front para armar links
    @Value("${frontend.reset-password.base-url}")
    private String resetPasswordBaseUrl;       // ej: https://frontend.com/reset-password?token=

    @Value("${frontend.order-details.base-url}")
    private String orderDetailsBaseUrl;        // ej: https://frontend.com/orders/  (le concatenamos el id)

    @Override
    public void sendResetPasswordEmail(EmailNotification notification) {
        if (notification.getType() != EmailType.RESET_PASSWORD) {
            throw new IllegalArgumentException("Email type must be RESET_PASSWORD");
        }

        try {
            String token = extractTokenFromPayload(notification.getPayloadJson());

            RecoveryEmailDTO dto = new RecoveryEmailDTO();
            dto.setTo(notification.getToAddress());
            dto.setLink(resetPasswordBaseUrl + token);

            rabbitTemplate.convertAndSend(emailExchange, recoveryRoutingKey, dto);
        } catch (Exception e) {
            // TODO: Log and/or update the notification status to FAILED using the repo
            throw new IllegalStateException("Error sending email to reset password to RabbitMQ", e);
        }
    }

    @Override
    public void sendOrderEmail(EmailNotification notification) {
        if (notification.getType() != EmailType.ORDER_CONFIRMATION && notification.getType() != EmailType.ORDER_STATUS_UPDATE) {
            throw new IllegalArgumentException("EmailNotification type must be ORDER_CONFIRMATION or ORDER_STATUS_UPDATE for this method");
        }

        try {
            JsonNode payload = notification.getPayloadJson() != null ? objectMapper.readTree(notification.getPayloadJson()) : null;

            Long orderId = notification.getRelatedOrderId();
            if (orderId == null && payload != null && payload.has("orderId")) {
                orderId = payload.get("orderId").asLong();
            }

            String status = payload != null && payload.has("status") ? payload.get("status").asText() : notification.getType().name();

            String detailsUrl;
            if (payload != null && payload.has("detailsUrl")) {
                detailsUrl = payload.get("detailsUrl").asText();
            } else {
                // fallback: uso base + orderId
                detailsUrl = orderDetailsBaseUrl + (orderId != null ? orderId : "");
            }

            OrderEmailDTO dto = new OrderEmailDTO(notification.getToAddress(), orderId, status, detailsUrl);

            rabbitTemplate.convertAndSend(emailExchange, orderRoutingKey, dto);
        } catch (Exception e) {
            // TODO: Log and/or update the notification status to FAILED using the repo
            throw new IllegalStateException("Error sending email to reset password to RabbitMQ", e);
        }
    }

    private String extractTokenFromPayload(String payloadJson) throws Exception {
        if (payloadJson == null || payloadJson.isBlank()) {
            throw new IllegalArgumentException("payloadJson is required to extract token");
        }
        JsonNode payload = objectMapper.readTree(payloadJson);
        if (!payload.has("token")) {
            throw new IllegalStateException("payloadJson does not contain 'token' field");
        }
        return payload.get("token").asText();
    }

}
