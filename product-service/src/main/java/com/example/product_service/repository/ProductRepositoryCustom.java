package com.example.product_service.repository;

import com.example.product_service.dtos.queries.ProductDtoQuery;
import com.example.product_service.entities.ProductEntity;
import org.springframework.data.domain.Page;

public interface ProductRepositoryCustom {
    Page<ProductEntity> getAllProductByNativeQuery(ProductDtoQuery dtoQuery);
}
