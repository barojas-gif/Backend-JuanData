package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.SemestresEntity;
import com.jdc.repojuandata.repository.SemestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemestresService extends GenericCrudService<SemestresEntity, Long> {

    @Autowired
    private SemestreRepository semestreRepository;

    @Override
    protected JpaRepository<SemestresEntity, Long> repository() {
        return semestreRepository;
    }

    public List<SemestresEntity> findByCarreraId(Long id) {
        return semestreRepository.findByCarreraId(id);
    }
}
