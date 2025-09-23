package com.example.authentication.services.impl;

import com.example.authentication.Role.RoleEnum;
import com.example.authentication.dtos.queris.UserDtoQuery;
import com.example.authentication.entities.UserEntity;
import com.example.authentication.repository.UserRepository;
import com.example.authentication.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl extends CommonServiceImpl<UserEntity, Long, UserRepository> implements UserService {
    public UserServicesImpl(UserRepository repo) {
        super(repo);
    }
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    @Override
    public UserEntity create(UserEntity user) {
        if (user.getRole() == null) {
            user.setRole(RoleEnum.USER); // mặc định USER
        }
        user.setPassword(encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public UserEntity updatePassword(UserEntity userEntity) {
        return repo.save(userEntity);
    }

    @Override
    public List<UserEntity> findAllUser() {
        return repo.findAll();
    }

    @Override
    public Optional<UserEntity> findUserById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Page<UserEntity> findAllUsers(UserDtoQuery dtoQuery, Pageable pageable) {
        return repo.findAllUserByNativeQuery(dtoQuery);
    }
}
