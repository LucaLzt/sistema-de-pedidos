package com.proyectos.sistemadepedidos.notifications.domain.model;

public enum NotificationStatus {
    PENDING,
    SENT,
    FAILED,
    RETRYING,
    DLQ
}
