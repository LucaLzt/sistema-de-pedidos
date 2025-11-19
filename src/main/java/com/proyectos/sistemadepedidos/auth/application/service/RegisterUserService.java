package com.proyectos.sistemadepedidos.auth.application.service;

import com.proyectos.sistemadepedidos.auth.application.port.in.RegisterUserCommand;
import com.proyectos.sistemadepedidos.auth.application.port.in.RegisterUserUseCase;
import com.proyectos.sistemadepedidos.auth.application.port.out.PasswordEncoderPort;
import com.proyectos.sistemadepedidos.auth.domain.model.Role;
import com.proyectos.sistemadepedidos.auth.domain.model.User;
import com.proyectos.sistemadepedidos.auth.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public void register(RegisterUserCommand command) {

        if (userRepository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        String encodedPassword = passwordEncoderPort.encode(command.rawPassword());

        User user = new User(
                null,
                null,
                null,
                command.name(),
                command.lastName(),
                command.email(),
                encodedPassword,
                Role.USER,
                null,
                true,
                null
        );

        userRepository.save(user);

    }
}
