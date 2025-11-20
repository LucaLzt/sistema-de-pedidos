package com.proyectos.sistemadepedidos.mail.application.port.in;

public interface SendMailUseCase {
    void send(SendMailCommand command);
}
