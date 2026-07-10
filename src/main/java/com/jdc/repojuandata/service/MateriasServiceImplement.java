package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.MateriasEntity;
import com.jdc.repojuandata.repository.MateriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MateriasServiceImplement implements IMateriasService{

    @Autowired
    private MateriasRepository materiasRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MateriasEntity> findAll(){
        return materiasRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public MateriasEntity findById (Long id){
        return materiasRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(MateriasEntity materiasEntity) {
        materiasRepository.save(materiasEntity);
    }

    @Override
    public void deleteById(Long id) {
        materiasRepository.deleteById(id);
    }

    @Override
    public List<MateriasEntity> findBySemestreId(Long id) {
        return materiasRepository.findBySemestreId(id);
    }

    @Override
    public List<MateriasEntity> findByCarreraAndSemestre(Long idCarrera, Long idSemestre) {
        return materiasRepository.findByCarreraAndSemestre(idCarrera, idSemestre);
    }


}
