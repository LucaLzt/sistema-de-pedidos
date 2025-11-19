package com.proyectos.sistemadepedidos.auth.infrastructure.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordResetRequest {
    private String email;
}
