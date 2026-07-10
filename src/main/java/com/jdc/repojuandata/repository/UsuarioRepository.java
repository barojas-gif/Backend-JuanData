package com.jdc.repojuandata.repository;

import com.jdc.repojuandata.DTO.UsuarioSimpleDTO;
import com.jdc.repojuandata.models.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuariosEntity, Long> {
    Optional<UsuariosEntity> findByCorreoUsuario(String correoUsuario);

    /*@Query("SELECT new com.jdc.repojuandata.DTO.UsuarioSimpleDTO(u.idUsuario, u.nombreUsuario, u.apellidoUsuario, u.numeroTelefono, u.numeroDocumento, u.carrerasEntity.nombreCarrera, u.rolesEntity.nombreRol) FROM UsuariosEntity u WHERE u.estado = 1")
    List<UsuarioSimpleDTO> listarUsuariosSimplificado();*/


    List<UsuariosEntity> findByCarrerasEntity_IdCarreraAndEstado(Long idCarrera, int estado);



    @Query("SELECT u FROM UsuariosEntity u WHERE u.estado = 1 ORDER BY u.idUsuario DESC")
    List<UsuariosEntity> findAllActivos();

    Optional<UsuariosEntity> findByNumeroDocumento(String numeroDocumento);




}

