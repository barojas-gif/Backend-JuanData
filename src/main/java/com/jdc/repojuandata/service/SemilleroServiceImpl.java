package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.SemilleroEntity;
import com.jdc.repojuandata.repository.SemilleroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SemilleroServiceImpl implements ISemilleroService {

    @Autowired
    private SemilleroRepository semilleroRepository;

    @Override
    @Transactional
    public List<SemilleroEntity> findAll() {
        return semilleroRepository.findAll();
    }

    @Override
    @Transactional
    public SemilleroEntity findById(Long id) {
        Optional<SemilleroEntity> optional = semilleroRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    @Transactional
    public void save(SemilleroEntity semilleroEntity) {
        semilleroRepository.save(semilleroEntity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        semilleroRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<SemilleroEntity> findByCarreraId(Long idCarrera) {
        return semilleroRepository.findByCarrera_IdCarrera(idCarrera);
    }
}
