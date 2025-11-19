package com.proyectos.sistemadepedidos.auth.domain.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class PasswordResetToken {

    private final Long id;
    private final Long userId;
    private final String token;
    private final Instant createdAt;
    private final Instant expiresAt;
    private final Instant usedAt;

    public PasswordResetToken(Long id, Long userId, String token, Instant createdAt, Instant expiresAt, Instant usedAt) {

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
