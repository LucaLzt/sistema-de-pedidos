package com.proyectos.sistemadepedidos.auth.infrastructure.persistence;

import com.proyectos.sistemadepedidos.auth.infrastructure.persistence.entity.RefreshTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataRefreshTokenRepository extends JpaRepository<RefreshTokenJpaEntity, Long> {

    Optional<RefreshTokenJpaEntity> findByToken(String token);

    List<RefreshTokenJpaEntity> findAllByUser_Id(Long userId);

    void deleteAllByUser_Id(Long userId);

}
