package com.example.product_service.services.impl;

import com.example.product_service.dtos.queries.ProductDtoQuery;
import com.example.product_service.entities.ProductEntity;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends CommonServiceImpl<ProductEntity, Long, ProductRepository> implements ProductService {
    public ProductServiceImpl(ProductRepository repo) {
        super(repo);
    }

    @Override
    public Page<ProductEntity> findAllByNativeQuery(ProductDtoQuery dtoQuery, Pageable pageable) {
        return repo.getAllProductByNativeQuery(dtoQuery);
    }
}
