package com.proyectos.sistemadepedidos.auth.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class User {

    private final Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;

    private final List<RefreshToken> refreshTokens;
    private final List<PasswordResetToken> passwordResetTokens;

    public User(Long id, List<RefreshToken> refreshTokens, List<PasswordResetToken> passwordResetTokens,
                String firstName, String lastName, String email, String password, Role role,
                Instant createdAt, boolean enabled, Instant updatedAt) {
        this.id = id;
        this.refreshTokens = refreshTokens;
        this.passwordResetTokens = passwordResetTokens;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.enabled = enabled;
        this.updatedAt = updatedAt;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

}
