package com.example.product_service.repository.impl;

import com.example.product_service.components.BaseNativeQuery;
import com.example.product_service.dtos.ConditionDto;
import com.example.product_service.dtos.queries.CategoryDtoQuery;
import com.example.product_service.entities.CategoryEntity;
import com.example.product_service.repository.CategoryRepositoryCustom;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {
    @Autowired
    private final BaseNativeQuery baseNativeQuery;

    public CategoryRepositoryCustomImpl(BaseNativeQuery baseNativeQuery) {
        this.baseNativeQuery = baseNativeQuery;
    }

    @Override
    public Page<CategoryEntity> getAllByCategoryByNativeQuery(CategoryDtoQuery dtoQuery) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT c.id, c.name , c.description , c.product_id as products FROM categories c WHERE 1=1");
        StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(*) FROM categories c WHERE 1=1");
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
        return baseNativeQuery.findPage(sqlBuilder.toString(), countSqlBuilder.toString(), pageable, CategoryEntity.class,conditionData.getParams());
    }
    private ConditionDto makeCondition(CategoryDtoQuery dtoQuery) {
        StringBuilder sqlBuilder = new StringBuilder();
        StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(*) FROM categories c WHERE 1=1");
        String name = dtoQuery.getName();
        String description = dtoQuery.getName();
        Map<String, Object> params = new HashMap<>();
        if (name != null && !name.isEmpty()) {
            sqlBuilder.append(" AND c.name LIKE :name");
            countSqlBuilder.append(" AND c.name LIKE :name");
            params.put("name", "%" + name + "%");
        }
        if (description != null) {
            sqlBuilder.append(" AND c.description LIKE :description");
            countSqlBuilder.append(" AND c.description LIKE :description");
            params.put("description","%" +description+ "%");
        }
        return ConditionDto.builder().params(params).queryCondition(sqlBuilder.toString()).build();
    }

}
