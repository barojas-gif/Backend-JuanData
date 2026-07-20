package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.PublicacionesEntity;
import com.jdc.repojuandata.repository.PublicacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PublicacionesService extends GenericCrudService<PublicacionesEntity, Long> {

    @Autowired
    private PublicacionesRepository publicacionesRepository;

    @Override
    protected JpaRepository<PublicacionesEntity, Long> repository() {
        return publicacionesRepository;
    }
}
