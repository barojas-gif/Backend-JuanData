package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.FacultadEntity;
import com.jdc.repojuandata.repository.FacultadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacultadServiceImplement implements IFacultadService{

    @Autowired
    private FacultadRepository facultadRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FacultadEntity> findAll(){
        return facultadRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public FacultadEntity findById(Long id){
        return facultadRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(FacultadEntity facultadEntity){
        facultadRepository.save(facultadEntity);
    }

    @Override
    @Transactional
    public void deleteById(Long id){
        facultadRepository.deleteById(id);
    }

}
