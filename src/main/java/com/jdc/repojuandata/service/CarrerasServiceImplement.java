package com.jdc.repojuandata.service;


import com.jdc.repojuandata.models.CarrerasEntity;
import com.jdc.repojuandata.repository.CarrerasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarrerasServiceImplement implements ICarrerasService{

    @Autowired
    private CarrerasRepository carrerasRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CarrerasEntity> findAll(){
        return carrerasRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CarrerasEntity findById(Long id){
        return carrerasRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(CarrerasEntity carrerasEntity){
        carrerasRepository.save(carrerasEntity);
    }

    @Override
    @Transactional
    public void deleteById(Long id){
        carrerasRepository.deleteById(id);
    }
}
