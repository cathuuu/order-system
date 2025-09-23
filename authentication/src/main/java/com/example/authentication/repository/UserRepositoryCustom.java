package com.example.authentication.repository;

import com.example.authentication.dtos.queris.UserDtoQuery;
import com.example.authentication.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryCustom {
    Page<UserEntity> findAllUserByNativeQuery(UserDtoQuery dtoQuery);
}
