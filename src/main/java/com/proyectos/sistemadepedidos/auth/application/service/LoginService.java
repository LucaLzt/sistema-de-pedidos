package com.proyectos.sistemadepedidos.auth.application.service;

import com.proyectos.sistemadepedidos.auth.application.port.in.LoginCommand;
import com.proyectos.sistemadepedidos.auth.application.port.in.LoginResult;
import com.proyectos.sistemadepedidos.auth.application.port.in.LoginUseCase;
import com.proyectos.sistemadepedidos.auth.application.port.out.PasswordEncoderPort;
import com.proyectos.sistemadepedidos.auth.application.port.out.TokenProviderPort;
import com.proyectos.sistemadepedidos.auth.domain.model.RefreshToken;
import com.proyectos.sistemadepedidos.auth.domain.model.User;
import com.proyectos.sistemadepedidos.auth.domain.repository.RefreshTokenRepository;
import com.proyectos.sistemadepedidos.auth.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService implements LoginUseCase {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenProviderPort tokenProviderPort;

    @Override
    public LoginResult login(LoginCommand command) {

        User user = userRepository.findByEmail(command.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

        boolean matches = passwordEncoderPort.matches(
                command.rawPassword(),
                user.getPassword()
        );

        if (!matches) {
            throw new IllegalArgumentException("Invalid password");
        }

        String accessToken = tokenProviderPort.generateAccessToken(user);
        String refreshTokenValue = tokenProviderPort.generateRefreshToken(user);

        RefreshToken refreshToken = new RefreshToken(
                null,
                user.getId(),
                refreshTokenValue,
                Instant.now(),
                Instant.now().plus(tokenProviderPort.getRefreshTokenTtl()),
                false
        );

        refreshTokenRepository.save(refreshToken);

        return new LoginResult(
                accessToken,
                refreshTokenValue
        );
    }

}
