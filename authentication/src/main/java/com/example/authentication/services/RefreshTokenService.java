package com.example.authentication.services;

import com.example.authentication.dtos.queris.RefreshTokenDtoQuery;
import com.example.authentication.entities.RefreshTokenEntity;
import com.example.authentication.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenService extends CommonService<RefreshTokenEntity, Long> {
    RefreshTokenEntity createToken(UserEntity user, Instant expiration);

    Optional<RefreshTokenEntity> findByToken(String token);
    
    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
    
    Optional<RefreshTokenEntity> deteteToken(String token);
    
    boolean revokeToken(String token);
    
    Optional<RefreshTokenEntity> deleteAllTokenForUser(Long userId);
    
    Optional<RefreshTokenEntity> isTokenValid(String token);

    Page<RefreshTokenEntity> findAllTokenByPage(RefreshTokenDtoQuery dtoQuery, Pageable pageable);
}
