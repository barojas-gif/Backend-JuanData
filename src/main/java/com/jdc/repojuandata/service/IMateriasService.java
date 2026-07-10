package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.MateriasEntity;

import java.util.List;

public interface IMateriasService {
    public List<MateriasEntity> findAll();
    public MateriasEntity findById(Long id);
    public void save(MateriasEntity materiasEntity);
    public void deleteById(Long id);
    List<MateriasEntity> findBySemestreId(Long id);

    List<MateriasEntity> findByCarreraAndSemestre(Long idCarrera, Long idSemestre);


}
