package com.example.authentication.repository;

import com.example.authentication.entities.RefreshTokenEntity;

import java.util.Optional;

public interface RefreshTokenRepository extends CommonRepository<RefreshTokenEntity, Long>, RefreshTokenRepositoryCustom {
    Optional<RefreshTokenEntity> findByToken(String token);

    Optional<RefreshTokenEntity> deleteAllById(Long userId);
}
