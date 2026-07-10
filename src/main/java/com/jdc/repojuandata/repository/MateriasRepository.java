package com.jdc.repojuandata.repository;

import com.jdc.repojuandata.models.MateriasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MateriasRepository extends JpaRepository<MateriasEntity, Long> {

    //Consulta para mostrar las materias por semestre
    @Query("SELECT m FROM MateriasEntity m WHERE m.semestresEntity.id_semestre = :idSemestre")
    List<MateriasEntity> findBySemestreId(@Param("idSemestre") Long id);

    @Query("SELECT m FROM MateriasEntity m WHERE m.carrerasEntity.idCarrera = :idCarrera AND m.semestresEntity.id_semestre = :idSemestre")
    List<MateriasEntity> findByCarreraAndSemestre(@Param("idCarrera") Long idCarrera, @Param("idSemestre") Long idSemestre);


}
