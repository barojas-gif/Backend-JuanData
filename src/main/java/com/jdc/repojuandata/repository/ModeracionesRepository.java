package com.jdc.repojuandata.repository;

import com.jdc.repojuandata.models.DocumentosEntity;
import com.jdc.repojuandata.models.ModeracionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModeracionesRepository extends JpaRepository<ModeracionesEntity, Long> {

    /*
    List<ModeracionesEntity> findByIdDocumento(Long idDocumento);

    @Query("SELECT m.documentosEntity FROM ModeracionesEntity m WHERE m.estadoModeracion = 0")
    List<DocumentosEntity> findDocumentosPendientesModeracion();

     */

}
