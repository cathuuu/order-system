package com.example.authentication.services.impl;

import com.example.authentication.exceptions.AppException;
import com.example.authentication.repository.CommonRepository;
import com.example.authentication.services.CommonService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class CommonServiceImpl <E, ID extends Serializable, R extends CommonRepository<E, ID>> implements CommonService<E, ID> {
    public CommonServiceImpl(R repo) {
        this.repo = repo;
    }
    protected final R repo;


    @Override
    public List<E> getAll() {
        return repo.findAll();
    }

    @Override
    public E getById(ID id) throws AppException {
        Optional<E> entityOptional = repo.findById(id);
        if (entityOptional.isPresent())
            throw new AppException("Entity not found");
        return entityOptional.get();
    }

    @Override
    public E save(E entity) {
        return repo.save(entity);
    }

    @Override
    public List<E> save(List<E> entities) {
        return repo.saveAll(entities);
    }

    @Override
    public boolean existsById(ID id) {
        return repo.existsById(id);
    }

    @Override
    public void deleteById(ID id) {
        repo.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<ID> ids) {
        repo.deleteByIdIn(ids);
    }
}