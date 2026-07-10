package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.FacultadEntity;

import java.util.List;

public interface IFacultadService {
    public List<FacultadEntity> findAll();
    public FacultadEntity findById(Long id);
    public void save(FacultadEntity facultadEntity);
    public void deleteById(Long id);
}
