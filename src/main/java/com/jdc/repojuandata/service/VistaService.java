package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.VistaEntity;
import com.jdc.repojuandata.repository.VistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VistaService {

    @Autowired
    private VistaRepository vistaRepository;

    public VistaEntity save(VistaEntity vista) {
        return vistaRepository.save(vista);
    }
}
