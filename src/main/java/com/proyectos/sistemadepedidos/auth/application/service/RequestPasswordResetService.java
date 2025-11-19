package com.proyectos.sistemadepedidos.auth.application.service;

import com.proyectos.sistemadepedidos.auth.application.port.in.RequestPasswordResetUseCase;
import com.proyectos.sistemadepedidos.auth.application.port.out.TokenProviderPort;
import com.proyectos.sistemadepedidos.auth.domain.model.PasswordResetToken;
import com.proyectos.sistemadepedidos.auth.domain.model.User;
import com.proyectos.sistemadepedidos.auth.domain.repository.PasswordResetRepository;
import com.proyectos.sistemadepedidos.auth.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestPasswordResetService implements RequestPasswordResetUseCase {

    private final UserRepository userRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final TokenProviderPort tokenProviderPort;

    @Override
    public void requestPasswordReset(String email) {
        // Do not reveal whether the email exists or not
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return;
        }

        String tokenValue = tokenProviderPort.generatePasswordResetToken(user);

        PasswordResetToken token = new PasswordResetToken(
                null,
                user.getId(),
                tokenValue,
                Instant.now(),
                Instant.now().plusSeconds(15 * 60), // 15 min
                null
        );

        passwordResetRepository.save(token);

        // Here we create the email, persist it, and send it through a port

    }
}
