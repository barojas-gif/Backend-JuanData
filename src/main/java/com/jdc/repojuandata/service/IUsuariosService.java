package com.jdc.repojuandata.service;

import com.jdc.repojuandata.DTO.UsuarioSimpleDTO;
import com.jdc.repojuandata.models.UsuariosEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUsuariosService {
    public List<UsuariosEntity> findAll();
    public UsuariosEntity findById(Long id);
    public UsuariosEntity save(UsuariosEntity usuariosEntity);
    public void deleteById(Long id);

    /*@Query("SELECT new com.jdc.repojuandata.DTO.UsuarioSimpleDTO(u.idUsuario, u.nombreUsuario, u.apellidoUsuario, u.numeroTelefono, u.numeroDocumento, u.carrerasEntity.nombreCarrera, u.rolesEntity.nombreRol) FROM UsuariosEntity u")
    List<UsuarioSimpleDTO> listarUsuariosSimplificado();*/

    UsuariosEntity update(Long id, UsuariosEntity usuarioActualizado);

    List<UsuarioSimpleDTO> listarUsuariosPorCarreraDelAdmin();

    boolean existeCorreo(String correo);

    boolean documentoExiste(String documento);
    //boolean registrarUsuarioConContrasenaTemporal(UsuariosEntity usuario);






}
