package com.example.product_service.services;

import com.example.product_service.dtos.queries.ProductDtoQuery;
import com.example.product_service.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService extends CommonService<ProductEntity, Long> {
Page<ProductEntity> findAllByNativeQuery(ProductDtoQuery dtoQuery, Pageable pageable);
}
