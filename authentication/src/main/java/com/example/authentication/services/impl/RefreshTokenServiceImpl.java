package com.example.authentication.services.impl;

import com.example.authentication.dtos.queris.RefreshTokenDtoQuery;
import com.example.authentication.entities.RefreshTokenEntity;
import com.example.authentication.entities.UserEntity;
import com.example.authentication.mappers.RefreshTokenMapper;
import com.example.authentication.mappers.UserMapper;
import com.example.authentication.repository.RefreshTokenRepository;
import com.example.authentication.services.RefreshTokenService;
import com.example.authentication.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl extends CommonServiceImpl<RefreshTokenEntity, Long, RefreshTokenRepository> implements RefreshTokenService {
    private final RefreshTokenMapper refreshTokenMapper;

    public RefreshTokenServiceImpl(RefreshTokenRepository repo, UserService userService, UserMapper userMapper, RefreshTokenMapper refreshTokenMapper) {
        super(repo);
        this.refreshTokenMapper = refreshTokenMapper;
    }

    @Override
    public RefreshTokenEntity createToken(UserEntity user, Instant expiration) {
        RefreshTokenEntity newToken = refreshTokenMapper.toEntity(user, expiration);
        return repo.save(newToken);
    }


    @Override
    public Optional<RefreshTokenEntity> findByToken(String token) {
        return repo.findByToken(token);
    }

    @Override
    public Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken) {
        return repo.findByToken(refreshToken);
    }

    @Override
    public Optional<RefreshTokenEntity> deteteToken(String token) {
        Optional<RefreshTokenEntity> tokenEntity = repo.findByToken(token);
        tokenEntity.ifPresent(repo::delete); // Nếu tồn tại, xóa
        return tokenEntity;
    }

    @Override
    public boolean revokeToken(String token) {
        Optional<RefreshTokenEntity> tokenEntity = repo.findByToken(token);
        tokenEntity.ifPresent(repo::delete);
        return tokenEntity.isPresent();
    }

    @Override
    public Optional<RefreshTokenEntity> deleteAllTokenForUser(Long userId) {
        return repo.deleteAllById(userId);
    }

    @Override
    public Optional<RefreshTokenEntity> isTokenValid(String token) {
        return  repo.findByToken(token);
    }

    @Override
    public Page<RefreshTokenEntity> findAllTokenByPage(RefreshTokenDtoQuery dtoQuery, Pageable pageable) {
        return repo.getRefreshToken(dtoQuery);
    }
}
