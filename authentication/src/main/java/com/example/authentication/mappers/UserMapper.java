package com.example.authentication.mappers;

import com.example.authentication.dtos.RegisterRequestDto;
import com.example.authentication.dtos.ResponseDto;
import com.example.authentication.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "username", source = "username") // map UserEntity -> userId
    @Mapping(target = "email",source = "email")
    @Mapping(target = "password", source = "password")
    UserEntity toEntity(RegisterRequestDto dto);

}
