package com.proyectos.sistemadepedidos.notifications.domain.exception;

import com.proyectos.sistemadepedidos.shared.domain.exception.InternalServerErrorException;

public class NotificationProcessingException extends InternalServerErrorException {
    public NotificationProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
