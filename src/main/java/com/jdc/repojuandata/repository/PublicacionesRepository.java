package com.jdc.repojuandata.repository;

import com.jdc.repojuandata.models.PublicacionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacionesRepository extends JpaRepository<PublicacionesEntity, Long> {

    /*
    List<PublicacionesEntity> findByTitulo(String titulo);
    List<PublicacionesEntity> findByIdCarrera(Long idCarrera);
    List<PublicacionesEntity> findByIdDocumento(Long idDocumento);

     */

}
