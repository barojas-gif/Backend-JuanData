package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.ModeracionesEntity;
import com.jdc.repojuandata.repository.ModeracionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModeracionesServiceImplement implements IModeracionesService{

    @Autowired
    private ModeracionesRepository moderacionesRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ModeracionesEntity> findAll(){
        return moderacionesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ModeracionesEntity findById(Long id){
        return moderacionesRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(ModeracionesEntity moderacionesEntity){
        moderacionesRepository.save(moderacionesEntity);
    }

    @Override
    @Transactional
    public void deletebyid(Long id){
        moderacionesRepository.deleteById(id);
    }
}
