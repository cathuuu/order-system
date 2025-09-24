package com.example.authentication.repository.impl;

import com.example.authentication.components.BaseNativeQuery;
import com.example.authentication.dtos.queris.ConditionDto;
import com.example.authentication.dtos.queris.UserDtoQuery;
import com.example.authentication.entities.UserEntity;
import com.example.authentication.repository.UserRepositoryCustom;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @Autowired
    private BaseNativeQuery baseNativeQuery;

    @Override
    public Page<UserEntity> findAllUserByNativeQuery(UserDtoQuery dtoQuery) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT u.id, u.username , u.password , u.email , u.role FROM users u WHERE 1=1");
        StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(*) FROM users u WHERE 1=1");
        String orderBy = dtoQuery.getSortBy();
        String orderDir = dtoQuery.getSortDir();
        ConditionDto conditionData = makeCondition(dtoQuery);
        if (!StringUtils.isBlank(conditionData.getQueryCondition())) {
            sqlBuilder.append(conditionData.getQueryCondition());
            countSqlBuilder.append(conditionData.getQueryCondition());
        }
        if (!StringUtils.isBlank(orderBy)) {
            if(!StringUtils.isBlank(orderDir)) sqlBuilder.append(" ORDER BY " + orderBy +" "+orderDir);
        }
        Pageable pageable = PageRequest.of(dtoQuery.getPage(),  dtoQuery.getSize());
        return baseNativeQuery.findPage(sqlBuilder.toString(), countSqlBuilder.toString(), pageable, UserEntity.class,conditionData.getParams());
    }
    private ConditionDto makeCondition(UserDtoQuery dtoQuery) {
        StringBuilder sqlBuilder = new StringBuilder();
        StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(*) FROM users u WHERE 1=1");
        String username = dtoQuery.getUsername();
        String email = dtoQuery.getEmail();
        Map<String, Object> params = new HashMap<>();
        if (username != null && !username.isEmpty()) {
            sqlBuilder.append(" AND u.username LIKE :username");
            countSqlBuilder.append(" AND u.username LIKE :username");
            params.put("username", "%" + username + "%");
        }
        if (email != null && !email.isEmpty()) {
            sqlBuilder.append(" AND u.email LIKE :email");
            countSqlBuilder.append(" AND u.email LIKE :email");
            params.put("email", "%" + email + "%");
        }
        return ConditionDto.builder().params(params).queryCondition(sqlBuilder.toString()).build();
    }
}
