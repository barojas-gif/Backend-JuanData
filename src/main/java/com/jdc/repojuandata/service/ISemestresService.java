package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.SemestresEntity;

import java.util.List;

public interface ISemestresService {
    public List<SemestresEntity>findAll();
    public SemestresEntity findById(Long id);
    public void save(SemestresEntity semestres);
    public void deleteById(Long id);
    List<SemestresEntity> findByCarreraId(Long id);
}
