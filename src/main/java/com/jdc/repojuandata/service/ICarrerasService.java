package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.CarrerasEntity;
import com.jdc.repojuandata.models.SemestresEntity;

import java.util.List;

public interface ICarrerasService {
    public List<CarrerasEntity> findAll();
    public CarrerasEntity findById(Long id);
    public void save(CarrerasEntity carreras);
    public void deleteById(Long id);



}
