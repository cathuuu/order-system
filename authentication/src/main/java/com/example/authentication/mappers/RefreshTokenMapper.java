package com.example.authentication.mappers;

import com.example.authentication.entities.RefreshTokenEntity;
import com.example.authentication.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {
    RefreshTokenMapper INSTANCE = Mappers.getMapper(RefreshTokenMapper.class);

    @Mapping(target = "userId", source = "user") // map UserEntity -> userId
    @Mapping(target = "token", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "expiryDate", source = "expiration")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    RefreshTokenEntity toEntity(UserEntity user, Instant expiration);
}
