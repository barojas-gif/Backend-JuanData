package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.SemilleroEntity;
import com.jdc.repojuandata.repository.SemilleroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemilleroService extends GenericCrudService<SemilleroEntity, Long> {

    @Autowired
    private SemilleroRepository semilleroRepository;

    @Override
    protected JpaRepository<SemilleroEntity, Long> repository() {
        return semilleroRepository;
    }

    public List<SemilleroEntity> findByCarreraId(Long idCarrera) {
        return semilleroRepository.findByCarrera_IdCarrera(idCarrera);
    }
}
