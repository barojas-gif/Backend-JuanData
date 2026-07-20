package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.CarrerasEntity;
import com.jdc.repojuandata.repository.CarrerasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CarrerasService extends GenericCrudService<CarrerasEntity, Long> {

    @Autowired
    private CarrerasRepository carrerasRepository;

    @Override
    protected JpaRepository<CarrerasEntity, Long> repository() {
        return carrerasRepository;
    }
}
