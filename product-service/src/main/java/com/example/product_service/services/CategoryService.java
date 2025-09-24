package com.example.product_service.services;

import com.example.product_service.dtos.queries.CategoryDtoQuery;
import com.example.product_service.entities.CategoryEntity;
import com.example.product_service.repository.CommonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService extends CommonService<CategoryEntity, Long> {
    Page<CategoryEntity> findAllByNativeQuery(CategoryDtoQuery dtoQuery ,Pageable pageable);
}
