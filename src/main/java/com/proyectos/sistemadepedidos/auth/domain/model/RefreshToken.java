package com.proyectos.sistemadepedidos.auth.domain.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class RefreshToken {

    private final Long id;
    private final Long userId;
    private final String token;
    private final Instant createdAt;
    private final Instant expiresAt;
    private final boolean revoked;

    public RefreshToken(Long id, Long userId, String token, Instant createdAt, Instant expiresAt, boolean revoked) {

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
        this.revoked = revoked;
    }

    public boolean isExpired() {
        return expiresAt.isBefore(Instant.now());
    }

    public boolean isActive() {
        return !revoked && !isExpired();
    }

    public RefreshToken revoke() {
        return new RefreshToken(
                this.id,
                this.userId,
                this.token,
                this.createdAt,
                this.expiresAt,
                true
        );
    }

}
