package com.jdc.repojuandata.repository;

import com.jdc.repojuandata.models.SemilleroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemilleroRepository extends JpaRepository<SemilleroEntity, Long> {

    List<SemilleroEntity> findByCarrera_IdCarrera(Long idCarrera);

}
