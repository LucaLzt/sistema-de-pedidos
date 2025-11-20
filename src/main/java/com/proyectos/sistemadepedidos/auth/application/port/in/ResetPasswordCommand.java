package com.proyectos.sistemadepedidos.auth.application.port.in;

public record ResetPasswordCommand(
        String token,
        String newPassword
) {
}
