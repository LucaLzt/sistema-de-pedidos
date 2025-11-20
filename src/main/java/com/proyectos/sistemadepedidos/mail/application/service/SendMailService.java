package com.proyectos.sistemadepedidos.mail.application.service;

import com.proyectos.sistemadepedidos.mail.application.port.in.SendMailCommand;
import com.proyectos.sistemadepedidos.mail.application.port.in.SendMailUseCase;
import com.proyectos.sistemadepedidos.mail.application.port.out.MailSenderPort;
import com.proyectos.sistemadepedidos.mail.domain.model.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SendMailService implements SendMailUseCase {

    private final MailSenderPort mailSenderPort;

    @Override
    public void send(SendMailCommand command) {
        Mail mail = command.html()
                ? Mail.html(command.to(), command.subject(), command.body())
                : Mail.plainText(command.to(), command.subject(), command.body());

        mailSenderPort.send(mail);
    }

}
