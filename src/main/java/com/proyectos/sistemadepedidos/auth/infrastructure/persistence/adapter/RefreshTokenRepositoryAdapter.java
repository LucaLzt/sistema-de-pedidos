package com.proyectos.sistemadepedidos.auth.infrastructure.persistence.adapter;

import com.proyectos.sistemadepedidos.auth.domain.model.RefreshToken;
import com.proyectos.sistemadepedidos.auth.domain.repository.RefreshTokenRepository;
import com.proyectos.sistemadepedidos.auth.infrastructure.persistence.SpringDataRefreshTokenRepository;
import com.proyectos.sistemadepedidos.auth.infrastructure.persistence.SpringDataUserRepository;
import com.proyectos.sistemadepedidos.auth.infrastructure.persistence.entity.RefreshTokenJpaEntity;
import com.proyectos.sistemadepedidos.auth.infrastructure.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {

    private final SpringDataRefreshTokenRepository springDataRefreshTokenRepository;
    private final SpringDataUserRepository springDataUserRepository;


    @Override
    public RefreshToken save(RefreshToken token) {
        UserJpaEntity user = springDataUserRepository.getReferenceById(token.getUserId());

        RefreshTokenJpaEntity entity = RefreshTokenJpaEntity.builder()
                .id(token.getId())
                .token(token.getToken())
                .createdAt(token.getCreatedAt())
                .expiresAt(token.getExpiresAt())
                .revoked(token.isRevoked())
                .user(user)
                .build();

        RefreshTokenJpaEntity saved = springDataRefreshTokenRepository.save(entity);
        return toDomain(saved);

    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return springDataRefreshTokenRepository.findByToken(token)
                .map(this::toDomain);
    }

    @Override
    public List<RefreshToken> findAllByUserId(Long userId) {
        return springDataRefreshTokenRepository.findAllByUser_Id(userId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        springDataRefreshTokenRepository.deleteById(id);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        springDataRefreshTokenRepository.deleteAllByUser_Id(userId);
    }

    private RefreshToken toDomain(RefreshTokenJpaEntity entity) {
        return RefreshToken.restore(
                entity.getId(),
                entity.getUser().getId(),
                entity.getToken(),
                entity.getCreatedAt(),
                entity.getExpiresAt(),
                entity.isRevoked()
        );
    }
}