package com.proyectos.sistemadepedidos.auth.domain.repository;

import com.proyectos.sistemadepedidos.auth.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    User save(User user);
    boolean existsByEmail(String email);
    void deleteById(Long id);

}
