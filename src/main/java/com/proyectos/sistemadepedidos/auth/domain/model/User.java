package com.proyectos.sistemadepedidos.auth.domain.model;

import lombok.Getter;

import java.time.Instant;

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

    private User(Long id, String firstName, String lastName, String email, String password,
                 Role role, Instant createdAt, boolean enabled, Instant updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.enabled = enabled;
        this.updatedAt = updatedAt;
    }

    public static User restore(Long id, String firstName, String lastName, String email, String password,
                               Role role, Instant createdAt, boolean enabled, Instant updatedAt) {
        return new User(id, firstName, lastName, email, password, role, createdAt, enabled, updatedAt);
    }

    public static User create(String firstName, String lastName, String email, String password, Role role) {
        return new User(
                null,
                firstName, lastName, email, password, role,
                Instant.now(),
                true,
                null
        );
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
