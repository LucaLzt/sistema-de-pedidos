package com.proyectos.sistemadepedidos.auth.domain.model;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;

@Getter
public class PasswordResetToken {

    private final Long id;
    private final Long userId;
    private final String token;
    private final Instant createdAt;
    private final Instant expiresAt;
    private final Instant usedAt;

    private PasswordResetToken(Long id, Long userId, String token, Instant createdAt, Instant expiresAt, Instant usedAt) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("token cannot be null or blank");
        }
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.usedAt = usedAt;
    }

    public static PasswordResetToken restore(Long id, Long userId, String token,
                                             Instant createdAt, Instant expiresAt, Instant usedAt) {
        return new PasswordResetToken(id, userId, token, createdAt, expiresAt, usedAt);
    }

    public static PasswordResetToken create(Long userId, String tokenValue) {
        return new PasswordResetToken(
                null,
                userId,
                tokenValue,
                Instant.now(),
                Instant.now().plus(Duration.ofMinutes(15)),
                null
        );
    }

    public boolean isExpired() {
        return expiresAt.isBefore(Instant.now());
    }

    public boolean isUsed() {
        return usedAt != null;
    }

    public boolean isActive() {
        return !isUsed() && !isExpired();
    }

    public PasswordResetToken markAsUsed(Instant usedAt) {
        return new PasswordResetToken(
                this.id,
                this.userId,
                this.token,
                this.createdAt,
                this.expiresAt,
                usedAt
        );
    }

}
