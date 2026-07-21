package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.ModeracionesEntity;
import com.jdc.repojuandata.repository.ModeracionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ModeracionesService extends GenericCrudService<ModeracionesEntity, Long> {

    @Autowired
    private ModeracionesRepository moderacionesRepository;

    @Override
    protected JpaRepository<ModeracionesEntity, Long> repository() {
        return moderacionesRepository;
    }
}
