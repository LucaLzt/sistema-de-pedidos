package com.proyectos.sistemadepedidos.mail.domain.model;

import lombok.Getter;

@Getter
public class Mail {

    private final String to;
    private final String subject;
    private final String body;
    private final boolean html;

    private Mail(String to, String subject, String body, boolean html) {
        if (to == null || to.isBlank()) {
            throw new IllegalArgumentException("Destination email (to) cannot be null or blank");
        }
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Subject cannot be null or blank");
        }
        if (body == null || body.isBlank()) {
            throw new IllegalArgumentException("Body cannot be null or blank");
        }
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.html = html;
    }

    public static Mail plainText(String to, String subject, String body) {
        return new Mail(to, subject, body, false);
    }

    public static Mail html(String to, String subject, String htmlBody) {
        return new Mail(to, subject, htmlBody, true);
    }

}
