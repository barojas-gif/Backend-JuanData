package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.MateriasEntity;
import com.jdc.repojuandata.repository.MateriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriasService extends GenericCrudService<MateriasEntity, Long> {

    @Autowired
    private MateriasRepository materiasRepository;

    @Override
    protected JpaRepository<MateriasEntity, Long> repository() {
        return materiasRepository;
    }

    public List<MateriasEntity> findBySemestreId(Long id) {
        return materiasRepository.findBySemestreId(id);
    }

    public List<MateriasEntity> findByCarreraAndSemestre(Long idCarrera, Long idSemestre) {
        return materiasRepository.findByCarreraAndSemestre(idCarrera, idSemestre);
    }
}
