package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.SemilleroEntity;

import java.util.List;

public interface ISemilleroService {
    List<SemilleroEntity> findAll();
    SemilleroEntity findById(Long id);
    void save(SemilleroEntity semilleroEntity);
    void deleteById(Long id);
    List<SemilleroEntity> findByCarreraId(Long idCarrera);
}
