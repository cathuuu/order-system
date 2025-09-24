package com.example.product_service.services.impl;

import com.example.product_service.dtos.queries.CategoryDtoQuery;
import com.example.product_service.entities.CategoryEntity;
import com.example.product_service.repository.CategoryRepository;
import com.example.product_service.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends CommonServiceImpl<CategoryEntity, Long, CategoryRepository> implements CategoryService {
    public CategoryServiceImpl(CategoryRepository repo) {
        super(repo);
    }

    @Override
    public Page<CategoryEntity> findAllByNativeQuery(CategoryDtoQuery dtoQuery, Pageable pageable) {
        return repo.getAllByCategoryByNativeQuery(dtoQuery);
    }
}
