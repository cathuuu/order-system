package com.example.product_service.repository;

import com.example.product_service.dtos.queries.CategoryDtoQuery;
import com.example.product_service.entities.CategoryEntity;
import org.springframework.data.domain.Page;

public interface CategoryRepositoryCustom {
    Page<CategoryEntity> getAllByCategoryByNativeQuery(CategoryDtoQuery dtoQuery);
}
