package com.proyectos.sistemadepedidos.auth.infrastructure.persistence;

import com.proyectos.sistemadepedidos.auth.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findById(Long id);

    Optional<UserJpaEntity> findByEmail(String email);

    boolean existsByEmail(String email);

}
