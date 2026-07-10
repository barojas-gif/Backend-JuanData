package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.ModeracionesEntity;

import java.util.List;

public interface IModeracionesService {
    public List<ModeracionesEntity> findAll();
    public ModeracionesEntity findById(Long id);
    public void save(ModeracionesEntity moderacionesEntity);
    public void deletebyid(Long id);
}
