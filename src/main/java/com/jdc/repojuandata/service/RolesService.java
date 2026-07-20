package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.RolesEntity;
import com.jdc.repojuandata.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RolesService extends GenericCrudService<RolesEntity, Long> {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    protected JpaRepository<RolesEntity, Long> repository() {
        return rolesRepository;
    }
}
