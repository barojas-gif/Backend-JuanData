package com.jdc.repojuandata.service;


import com.jdc.repojuandata.models.PublicacionesEntity;
import com.jdc.repojuandata.repository.PublicacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublicacionesServiceImplement implements IPublicacionesService{

    @Autowired
    private PublicacionesRepository publicacionesRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PublicacionesEntity> findAll(){
        return publicacionesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PublicacionesEntity findById(Long id){
        return publicacionesRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(PublicacionesEntity publicacionesEntity){
        publicacionesRepository.save(publicacionesEntity);
    }

    @Override
    @Transactional
    public void deleteById(Long id){
        publicacionesRepository.deleteById(id);
    }
}
