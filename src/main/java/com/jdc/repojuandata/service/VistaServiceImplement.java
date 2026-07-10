package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.VistaEntity;
import com.jdc.repojuandata.repository.VistaRepository;
import com.jdc.repojuandata.service.IVistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VistaServiceImplement  implements IVistaService{

    @Autowired
    private VistaRepository vistaRepository;

    @Override
    public VistaEntity save(VistaEntity vista) {
        return vistaRepository.save(vista);
    }

}
