package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.RolesEntity;

import java.util.List;

public interface IRolesService {
    public List<RolesEntity> findAll();
    public RolesEntity findById(Long id);
    public void save(RolesEntity rolesEntity);
    public void deleteById(Long id);
}
