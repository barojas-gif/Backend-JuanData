package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.PublicacionesEntity;

import java.util.List;

public interface IPublicacionesService {
    public List <PublicacionesEntity> findAll();
    public PublicacionesEntity findById(Long id);
    public void save(PublicacionesEntity publicacionesEntity);
    public void deleteById( Long id);
}
