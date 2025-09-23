package com.example.authentication.repository.impl;

import com.example.authentication.compnents.BaseNativeQuery;
import com.example.authentication.dtos.queris.ConditionDto;
import com.example.authentication.dtos.queris.RefreshTokenDtoQuery;
import com.example.authentication.entities.RefreshTokenEntity;
import com.example.authentication.repository.RefreshTokenRepositoryCustom;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RefreshTokenRepositoryCustomImpl implements RefreshTokenRepositoryCustom {
    @Autowired
    private BaseNativeQuery baseNativeQuery;
    @Override
    public Page<RefreshTokenEntity> getRefreshToken(RefreshTokenDtoQuery dtoQuery) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT r.id, r.token , r.user_id as userId, r.created_at as createdAt FROM refresh_tokens r WHERE 1=1");
        StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(*) FROM refresh_tokens r WHERE 1=1");
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
        return baseNativeQuery.findPage(sqlBuilder.toString(), countSqlBuilder.toString(), pageable, RefreshTokenEntity.class,conditionData.getParams());
    }
    private ConditionDto makeCondition(RefreshTokenDtoQuery dtoQuery) {
        StringBuilder sqlBuilder = new StringBuilder();
        StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(*) FROM refresh_tokens r WHERE 1=1");
        String token = dtoQuery.getToken();
        Long userId = dtoQuery.getUserId();
        Instant expiryDate = dtoQuery.getExpiryDate();
        Map<String, Object> params = new HashMap<>();
        if (token != null && !token.isEmpty()) {
            sqlBuilder.append(" AND r.token LIKE :token");
            countSqlBuilder.append(" AND r.token LIKE :token");
            params.put("token", "%" + token + "%");
        }
        if (userId != null) {
            sqlBuilder.append(" AND r.id = :userId");
            countSqlBuilder.append(" AND r.id = :userId");
            params.put("userId", userId);
        }
        if (expiryDate != null) {
            sqlBuilder.append(" AND r.expiry_date LIKE :expiryDate");
            countSqlBuilder.append(" AND u.expiry_date LIKE :expiryDate");
            params.put("expiryDate", "%" + expiryDate + "%");
        }
        return ConditionDto.builder().params(params).queryCondition(sqlBuilder.toString()).build();
    }
}
