package com.proyectos.sistemadepedidos.notifications.infrastructure.persistence.entity;

import com.proyectos.sistemadepedidos.notifications.domain.model.EmailType;
import com.proyectos.sistemadepedidos.notifications.domain.model.NotificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "email_notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailNotificationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EmailType type;

    @Column(name = "to_address", nullable = false)
    private String toAddress;

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private NotificationStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    private Instant sentAt;

    @Column(length = 1000)
    private String errorMessage;

    private Long relatedOrderId;

    @Lob
    private String payloadJson;

}
