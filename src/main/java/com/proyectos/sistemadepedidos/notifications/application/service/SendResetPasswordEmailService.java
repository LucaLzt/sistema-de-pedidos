package com.proyectos.sistemadepedidos.notifications.application.service;

import com.proyectos.sistemadepedidos.notifications.application.in.ResetPasswordEmailCommand;
import com.proyectos.sistemadepedidos.notifications.application.in.SendResetPasswordEmailUseCase;
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
public class SendResetPasswordEmailService implements SendResetPasswordEmailUseCase {

    private final EmailNotificationRepository emailNotificationRepository;
    private final EmailSenderPort emailSenderPort;

    @Override
    public void sendResetPasswordEmail(ResetPasswordEmailCommand command) {
        String payloadJson = """
                { "token": "%s" }
                """.formatted(command.token());

        EmailNotification notification = new EmailNotification(
                null,
                command.email(),
                "Reset your password",
                "Use the link provided to reset your password",
                EmailType.RESET_PASSWORD,
                NotificationStatus.PENDING,
                Instant.now(),
                null,
                null,
                null,
                payloadJson
        );

        EmailNotification saved = emailNotificationRepository.save(notification);

        emailSenderPort.sendResetPasswordEmail(saved);
    }
}
