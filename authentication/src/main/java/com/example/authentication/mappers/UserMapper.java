package com.example.authentication.mappers;

import com.example.authentication.dtos.RegisterRequestDto;
import com.example.authentication.dtos.ResponseDto;
import com.example.authentication.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(RegisterRequestDto dto) {
        if (dto == null) return null;

        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }
}
