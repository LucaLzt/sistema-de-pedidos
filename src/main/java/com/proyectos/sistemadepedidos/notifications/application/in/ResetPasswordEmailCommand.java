package com.proyectos.sistemadepedidos.notifications.application.in;

public record ResetPasswordEmailCommand(
        String email,
        String token
) {
}
