package com.example.authentication.repository;

import com.example.authentication.dtos.queris.RefreshTokenDtoQuery;
import com.example.authentication.entities.RefreshTokenEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;


@Repository
public interface RefreshTokenRepositoryCustom {
    Page<RefreshTokenEntity> getRefreshToken(RefreshTokenDtoQuery dtoQuery);
}
