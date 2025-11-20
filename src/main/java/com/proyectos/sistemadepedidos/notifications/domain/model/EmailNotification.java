package com.proyectos.sistemadepedidos.notifications.domain.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class EmailNotification {

    private final Long id;
    private final String toAddress;
    private final String subject;
    private final String body;
    private final EmailType type;
    private final NotificationStatus status;
    private final Instant createdAt;
    private final Instant sentAt;
    private final String errorMessage;
    private final Long relatedOrderId;
    private final String payloadJson;


    private EmailNotification(Long id, String toAddress, String subject, String body, EmailType type,
                              NotificationStatus status, Instant createdAt, Instant sentAt, String errorMessage,
                              Long relatedOrderId, String payloadJson) {
        if (type == null) {
            throw new IllegalArgumentException("Email type cannot be null");
        }
        if (toAddress == null || toAddress.isBlank()) {
            throw new IllegalArgumentException("To address cannot be null or empty");
        }
        this.id = id;
        this.toAddress = toAddress;
        this.subject = subject;
        this.body = body;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.sentAt = sentAt;
        this.errorMessage = errorMessage;
        this.relatedOrderId = relatedOrderId;
        this.payloadJson = payloadJson;
    }

    /**
     * Exclusive method for the Repository to rebuild the entity.
     * DO NOT use to create new notifications from business logic.
     */
    public static EmailNotification restore(
            Long id, String toAddress, String subject, String body, EmailType type,
            NotificationStatus status, Instant createdAt, Instant sentAt,
            String errorMessage, Long relatedOrderId, String payloadJson
    ) {
        return new EmailNotification(
                id, toAddress, subject, body, type, status, createdAt,
                sentAt, errorMessage, relatedOrderId, payloadJson
        );
    }

    public static EmailNotification createResetPassword(String toAddress, String payloadJson) {
        return new EmailNotification(
                null,
                toAddress,
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
    }

    public static EmailNotification createOrderStatusUpdate(String toAddress, String subject, String body, Long orderId, String payloadJson) {
        return new EmailNotification(
                null,
                toAddress,
                subject,
                body,
                EmailType.ORDER_STATUS_UPDATE,
                NotificationStatus.PENDING,
                Instant.now(),
                null,
                null,
                orderId,
                payloadJson
        );
    }

    public EmailNotification markAsSent(Instant sentAt) {
        return new EmailNotification(
                this.id,
                this.toAddress,
                this.subject,
                this.body,
                this.type,
                NotificationStatus.SENT,
                this.createdAt,
                sentAt,
                null,
                this.relatedOrderId,
                this.payloadJson
        );
    }

    public EmailNotification markAsFailed(String errorMessage) {
        return new EmailNotification(
                this.id,
                this.toAddress,
                this.subject,
                this.body,
                this.type,
                NotificationStatus.FAILED,
                this.createdAt,
                this.sentAt,
                errorMessage,
                this.relatedOrderId,
                this.payloadJson
        );
    }

}
