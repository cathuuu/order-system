package com.example.authentication.services;


import com.example.authentication.dtos.queris.UserDtoQuery;
import com.example.authentication.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService extends CommonService<UserEntity, Long> {
    UserEntity create (UserEntity user);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    UserEntity updatePassword(UserEntity userEntity);

    List<UserEntity> findAllUser();

    Optional<UserEntity> findUserById(Long id);

    Page<UserEntity> findAllUsers(UserDtoQuery dtoQuery, Pageable pageable);

}
