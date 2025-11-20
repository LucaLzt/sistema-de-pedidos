package com.proyectos.sistemadepedidos.auth.infrastructure.persistence.adapter;

import com.proyectos.sistemadepedidos.auth.domain.model.PasswordResetToken;
import com.proyectos.sistemadepedidos.auth.domain.repository.PasswordResetRepository;
import com.proyectos.sistemadepedidos.auth.infrastructure.persistence.SpringDataPasswordResetTokenRepository;
import com.proyectos.sistemadepedidos.auth.infrastructure.persistence.SpringDataUserRepository;
import com.proyectos.sistemadepedidos.auth.infrastructure.persistence.entity.PasswordResetTokenJpaEntity;
import com.proyectos.sistemadepedidos.auth.infrastructure.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PasswordResetTokenRepositoryAdapter implements PasswordResetRepository {

    private final SpringDataPasswordResetTokenRepository springDataPasswordResetTokenRepository;
    private final SpringDataUserRepository springDataUserRepository;


    @Override
    public PasswordResetToken save(PasswordResetToken token) {
        UserJpaEntity user = springDataUserRepository.getReferenceById(token.getUserId());

        PasswordResetTokenJpaEntity entity = PasswordResetTokenJpaEntity.builder()
                .id(token.getId())
                .token(token.getToken())
                .createdAt(token.getCreatedAt())
                .expiresAt(token.getExpiresAt())
                .usedAt(token.getUsedAt())
                .user(user)
                .build();

        PasswordResetTokenJpaEntity saved = springDataPasswordResetTokenRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return springDataPasswordResetTokenRepository.findByToken(token)
                .map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        springDataPasswordResetTokenRepository.deleteById(id);
    }

    @Override
    public void deleteExpiredTokens() {
        springDataPasswordResetTokenRepository.deleteByExpiresAtBefore(Instant.now());
    }

    @Override
    public void deleteByUserId(Long userId) {
        springDataPasswordResetTokenRepository.deleteByUser_Id(userId);
    }

    private PasswordResetToken toDomain(PasswordResetTokenJpaEntity entity) {
        return PasswordResetToken.restore(
                entity.getId(),
                entity.getUser().getId(),
                entity.getToken(),
                entity.getCreatedAt(),
                entity.getExpiresAt(),
                entity.getUsedAt()
        );
    }
}
