package com.proyectos.sistemadepedidos.mail.application.port.out;

import com.proyectos.sistemadepedidos.mail.domain.model.Mail;

public interface MailSenderPort {
    void send(Mail mail);
}
