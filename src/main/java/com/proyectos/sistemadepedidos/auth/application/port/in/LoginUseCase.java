package com.proyectos.sistemadepedidos.auth.application.port.in;

public interface LoginUseCase {

    LoginResult login(LoginCommand command);

}
