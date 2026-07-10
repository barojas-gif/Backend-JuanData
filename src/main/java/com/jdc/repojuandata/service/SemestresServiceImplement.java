package com.jdc.repojuandata.service;


import com.jdc.repojuandata.models.SemestresEntity;
import com.jdc.repojuandata.repository.SemestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SemestresServiceImplement implements ISemestresService{

    @Autowired
    private SemestreRepository semestreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SemestresEntity> findAll(){
        return semestreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public SemestresEntity findById(Long id){
        return semestreRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(SemestresEntity semestresEntity){
        semestreRepository.save(semestresEntity);
    }

    @Override
    public void deleteById(Long id){
        semestreRepository.deleteById(id);
    }

    @Override
    public List<SemestresEntity> findByCarreraId(Long id) {
        return semestreRepository.findByCarreraId(id);
    }

}
