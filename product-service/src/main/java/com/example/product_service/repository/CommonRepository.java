package com.example.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;


@NoRepositoryBean
public interface CommonRepository<E, ID> extends JpaRepository<E, ID> {
    <ID extends Serializable> void deleteByIdIn(List<ID> ids);
}
