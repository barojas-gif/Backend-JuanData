package com.jdc.repojuandata.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class GenericCrudService<T, ID> {

    protected abstract JpaRepository<T, ID> repository();

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository().findAll();
    }

    @Transactional(readOnly = true)
    public T findById(ID id) {
        return repository().findById(id).orElse(null);
    }

    @Transactional
    public void save(T entity) {
        repository().save(entity);
    }

    @Transactional
    public void deleteById(ID id) {
        repository().deleteById(id);
    }
}
