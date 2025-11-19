package com.proyectos.sistemadepedidos.auth.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenResponse {
    private final String accessToken;
}
