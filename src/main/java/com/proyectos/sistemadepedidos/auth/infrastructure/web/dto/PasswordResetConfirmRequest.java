package com.proyectos.sistemadepedidos.auth.infrastructure.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordResetConfirmRequest {
    private String token;
    private String newPassword;
}
