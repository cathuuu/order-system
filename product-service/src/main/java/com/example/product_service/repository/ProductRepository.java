package com.example.product_service.repository;

import com.example.product_service.entities.ProductEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CommonRepository<ProductEntity, Long> , ProductRepositoryCustom {
}
