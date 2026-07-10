package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.RolesEntity;
import com.jdc.repojuandata.models.UsuariosEntity;
import com.jdc.repojuandata.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolesServiceImplement implements IRolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RolesEntity>findAll(){
        return rolesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public RolesEntity findById(Long id){
        return rolesRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(RolesEntity rolesEntity){
        rolesRepository.save(rolesEntity);
    }

    @Override
    public void deleteById(Long id){
        rolesRepository.deleteById(id);
    }
}
