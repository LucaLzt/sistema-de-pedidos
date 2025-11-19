package com.proyectos.sistemadepedidos.auth.application.service;

import com.proyectos.sistemadepedidos.auth.application.port.in.ResetPasswordCommand;
import com.proyectos.sistemadepedidos.auth.application.port.in.ResetPasswordUseCase;
import com.proyectos.sistemadepedidos.auth.application.port.out.PasswordEncoderPort;
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
public class ResetPasswordService implements ResetPasswordUseCase {

    private final PasswordResetRepository passwordResetRepository;
    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public void resetPassword(ResetPasswordCommand command) {
        PasswordResetToken token = passwordResetRepository.findByToken(command.token())
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));

        if (token.getExpiresAt().isBefore(Instant.now()) || token.getUsedAt() != null) {
            throw new IllegalArgumentException("Token has expired");
        }

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String encodedPassword = passwordEncoderPort.encode(command.newPassword());
        user.changePassword(encodedPassword);

        userRepository.save(user);

        token.markAsUsed(Instant.now());
        passwordResetRepository.save(token);
    }
}
