package com.example.product_service.repository;

import com.example.product_service.entities.CategoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CommonRepository<CategoryEntity, Long>, CategoryRepositoryCustom {
}