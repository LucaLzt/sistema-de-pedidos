package com.proyectos.sistemadepedidos.auth.infrastructure.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
