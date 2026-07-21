package com.jdc.repojuandata.service;

import com.jdc.repojuandata.DTO.UsuarioSimpleDTO;
import com.jdc.repojuandata.models.UsuariosEntity;
import com.jdc.repojuandata.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuariosService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @Transactional(readOnly = true)
    public List<UsuariosEntity> findAll(){
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UsuariosEntity findById(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    @Transactional
    public UsuariosEntity save(UsuariosEntity usuariosEntity){
        if (usuariosEntity.getContrasenaUsuario() != null && !usuariosEntity.getContrasenaUsuario().isBlank()) {
            String contraseñaEncriptada = passwordEncoder.encode(usuariosEntity.getContrasenaUsuario());
            usuariosEntity.setContrasenaUsuario(contraseñaEncriptada);
        }

        return usuarioRepository.save(usuariosEntity); // <- retorna el objeto guardado
    }

    @Transactional
    public void deleteById(Long id){
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public UsuariosEntity update(Long id, UsuariosEntity usuarioActualizado) {
        UsuariosEntity existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        existente.setNombreUsuario(usuarioActualizado.getNombreUsuario());
        existente.setApellidoUsuario(usuarioActualizado.getApellidoUsuario());
        existente.setCorreoUsuario(usuarioActualizado.getCorreoUsuario());
        existente.setRolesEntity(usuarioActualizado.getRolesEntity());
        existente.setCarrerasEntity(usuarioActualizado.getCarrerasEntity());
        return usuarioRepository.save(existente);
    }

    public List<UsuarioSimpleDTO> listarUsuariosPorCarreraDelAdmin() {
        UsuariosEntity usuarioActual = authenticatedUserService.getUsuarioAutenticado();

        Long idCarrera = usuarioActual.getCarrerasEntity().getIdCarrera();

        List<UsuariosEntity> usuarios = usuarioRepository.findByCarrerasEntity_IdCarreraAndEstado(idCarrera, 1);

        return usuarios.stream().map(usuario -> {
            UsuarioSimpleDTO dto = new UsuarioSimpleDTO();
            dto.setId_usuario(usuario.getIdUsuario());
            dto.setNombre_usuario(usuario.getNombreUsuario());
            dto.setApellido_usuario(usuario.getApellidoUsuario());
            dto.setDocumento_usuario(usuario.getNumeroDocumento());
            dto.setTelefono_usuario(usuario.getNumeroTelefono());
            dto.setNombre_carrera(usuario.getCarrerasEntity().getNombreCarrera());
            dto.setNombre_rol(usuario.getRolesEntity().getNombreRol());
            return dto;
        }).collect(Collectors.toList());
    }

    public boolean existeCorreo(String correo) {
        return usuarioRepository.findByCorreoUsuario(correo).isPresent();
    }

    public boolean documentoExiste(String documento) {
        return usuarioRepository.findByNumeroDocumento(documento).isPresent();
    }
}
