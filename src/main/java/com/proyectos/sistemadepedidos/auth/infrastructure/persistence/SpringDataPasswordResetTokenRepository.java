package com.proyectos.sistemadepedidos.auth.infrastructure.persistence;

import com.proyectos.sistemadepedidos.auth.infrastructure.persistence.entity.PasswordResetTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface SpringDataPasswordResetTokenRepository extends JpaRepository<PasswordResetTokenJpaEntity, Long> {

    Optional<PasswordResetTokenJpaEntity> findByToken(String token);
    void deleteByUser_Id(Long userId);
    void deleteByExpiresAtBefore(Instant now);

}
