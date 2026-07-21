package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.FacultadEntity;
import com.jdc.repojuandata.repository.FacultadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class FacultadService extends GenericCrudService<FacultadEntity, Long> {

    @Autowired
    private FacultadRepository facultadRepository;

    @Override
    protected JpaRepository<FacultadEntity, Long> repository() {
        return facultadRepository;
    }
}
