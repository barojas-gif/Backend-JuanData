package com.jdc.repojuandata.repository;

import com.jdc.repojuandata.models.SemestresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SemestreRepository  extends JpaRepository<SemestresEntity, Long> {

    @Query(value = "SELECT DISTINCT s.* FROM semestres s " +
            "JOIN materias m ON m.id_semestre = s.id_semestre " +
            "WHERE m.id_carrera = :idCarrera", nativeQuery = true)
    List<SemestresEntity> findByCarreraId(@Param("idCarrera") Long id);



}
