package com.proyectos.sistemadepedidos.auth.application.port.in;

public interface RefreshTokenUseCase {

    String refresh(String refreshToken);

}
