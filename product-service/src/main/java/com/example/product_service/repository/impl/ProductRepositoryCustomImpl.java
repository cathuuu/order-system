package com.example.product_service.repository.impl;

import com.example.product_service.components.BaseNativeQuery;
import com.example.product_service.dtos.ConditionDto;
import com.example.product_service.dtos.queries.ProductDtoQuery;
import com.example.product_service.entities.ProductEntity;
import com.example.product_service.repository.ProductRepositoryCustom;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    @Autowired
    private final BaseNativeQuery  baseNativeQuery;

    public ProductRepositoryCustomImpl(BaseNativeQuery baseNativeQuery) {
        this.baseNativeQuery = baseNativeQuery;
    }

    @Override
    public Page<ProductEntity> getAllProductByNativeQuery(ProductDtoQuery dtoQuery) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT p.id, p.name , p.description ,p.price , p.category_id as categories , p.stock as stock FROM products p WHERE 1=1");
        StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(*) FROM products p WHERE 1=1");
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
        return baseNativeQuery.findPage(sqlBuilder.toString(), countSqlBuilder.toString(), pageable, ProductEntity.class,conditionData.getParams());
    }
    private ConditionDto makeCondition(ProductDtoQuery dtoQuery) {
        StringBuilder sqlBuilder = new StringBuilder();
        StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(*) FROM products p WHERE 1=1");
        String name = dtoQuery.getName();
        String description = dtoQuery.getName();
        BigDecimal price = dtoQuery.getPrice();
        Integer stock = dtoQuery.getStock();
        Map<String, Object> params = new HashMap<>();
        if (name != null && !name.isEmpty()) {
            sqlBuilder.append(" AND p.name LIKE :name");
            countSqlBuilder.append(" AND p.name LIKE :name");
            params.put("name", "%" + name + "%");
        }
        if (description != null && !name.isEmpty()) {
            sqlBuilder.append(" AND p.description LIKE :description");
            countSqlBuilder.append(" AND p.description LIKE :description");
            params.put("description","%" +description+ "%");
        }
        if (price != null) {
            sqlBuilder.append(" AND p.price = :price");
            countSqlBuilder.append(" AND p.price = :price");
            params.put("price", price);
        }
        if (stock != null) {
            sqlBuilder.append(" AND p.stock = :stock");
            countSqlBuilder.append(" AND p.stock = :stock");
            params.put("stock", price);
        }
        return ConditionDto.builder().params(params).queryCondition(sqlBuilder.toString()).build();
    }
}
