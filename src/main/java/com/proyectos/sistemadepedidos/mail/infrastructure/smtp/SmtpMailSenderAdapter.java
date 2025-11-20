package com.proyectos.sistemadepedidos.mail.infrastructure.smtp;

import com.proyectos.sistemadepedidos.mail.application.port.out.MailSenderPort;
import com.proyectos.sistemadepedidos.mail.domain.model.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmtpMailSenderAdapter implements MailSenderPort {

    private final JavaMailSender javaMailSender;

    @Value("${mail.default-from}")
    private String defaultFrom;

    @Override
    public void send(Mail mail) {
        if (mail.isHtml()) {
            sendHtml(mail);
        } else {
            sendPlainText(mail);
        }
    }

    private void sendPlainText(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(defaultFrom);
        message.setTo(mail.getTo());
        message.setSubject(mail.getSubject());
        message.setText(mail.getBody());
        javaMailSender.send(message);
    }

    private void sendHtml(Mail mail) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody(), true); // true => interpreta como HTML

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // acá podés loggear, lanzar excepción custom, etc.
            throw new IllegalStateException("Error enviando mail HTML", e);
        }
    }

}
