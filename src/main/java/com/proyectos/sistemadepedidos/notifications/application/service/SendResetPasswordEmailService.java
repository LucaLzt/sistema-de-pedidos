package com.proyectos.sistemadepedidos.notifications.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectos.sistemadepedidos.notifications.application.in.ResetPasswordEmailCommand;
import com.proyectos.sistemadepedidos.notifications.application.in.SendResetPasswordEmailUseCase;
import com.proyectos.sistemadepedidos.notifications.application.out.EmailSenderPort;
import com.proyectos.sistemadepedidos.notifications.domain.model.EmailNotification;
import com.proyectos.sistemadepedidos.notifications.domain.repository.EmailNotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class SendResetPasswordEmailService implements SendResetPasswordEmailUseCase {

    private final EmailNotificationRepository emailNotificationRepository;
    private final EmailSenderPort emailSenderPort;
    private final ObjectMapper objectMapper;

    @Override
    public void sendResetPasswordEmail(ResetPasswordEmailCommand command) {

        String payloadJson;
        try {
            Map<String, Object> payloadData = Map.of(
                    "token", command.token()
            );
            payloadJson = objectMapper.writeValueAsString(payloadData);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error creating JSON payload for order email");
        }

        EmailNotification notification = EmailNotification.createResetPassword(
                command.email(),
                payloadJson
        );

        EmailNotification saved = emailNotificationRepository.save(notification);

        emailSenderPort.sendResetPasswordEmail(saved);
    }
}
