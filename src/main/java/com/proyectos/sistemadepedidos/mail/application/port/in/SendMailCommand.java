package com.proyectos.sistemadepedidos.mail.application.port.in;

public record SendMailCommand(
        String to,
        String subject,
        String body,
        boolean html
) {
}
