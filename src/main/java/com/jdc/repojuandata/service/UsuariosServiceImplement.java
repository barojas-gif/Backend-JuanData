package com.jdc.repojuandata.service;

import com.jdc.repojuandata.DTO.UsuarioSimpleDTO;
import com.jdc.repojuandata.config.EmailService;
import com.jdc.repojuandata.models.UsuariosEntity;
import com.jdc.repojuandata.repository.SemilleroRepository;
import com.jdc.repojuandata.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UsuariosServiceImplement implements IUsuariosService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    @Autowired
    private SemilleroRepository semilleroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UsuariosEntity> findAll(){
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuariosEntity findById(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public UsuariosEntity save(UsuariosEntity usuariosEntity){
        if (usuariosEntity.getContrasenaUsuario() != null && !usuariosEntity.getContrasenaUsuario().isBlank()) {
            String contraseñaEncriptada = passwordEncoder.encode(usuariosEntity.getContrasenaUsuario());
            usuariosEntity.setContrasenaUsuario(contraseñaEncriptada);
        }

        return usuarioRepository.save(usuariosEntity); // <- retorna el objeto guardado
    }

    @Override
    @Transactional
    public void deleteById(Long id){
        usuarioRepository.deleteById(id);
    }

    @Override
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

    @Override
    public List<UsuarioSimpleDTO> listarUsuariosPorCarreraDelAdmin() {
        // 1. Obtener el correo del usuario autenticado
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Buscar el usuario completo por su correo
        UsuariosEntity usuarioActual = usuarioRepository.findByCorreoUsuario(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 3. Obtener la carrera del usuario
        Long idCarrera = usuarioActual.getCarrerasEntity().getIdCarrera();

        // 4. Buscar usuarios activos con esa carrera
        List<UsuariosEntity> usuarios = usuarioRepository.findByCarrerasEntity_IdCarreraAndEstado(idCarrera, 1);

        // 5. Convertir a DTOs
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

    @Override
    public boolean existeCorreo(String correo) {
        return usuarioRepository.findByCorreoUsuario(correo).isPresent();
    }
    @Override
    public boolean documentoExiste(String documento) {
        return usuarioRepository.findByNumeroDocumento(documento).isPresent();
    }

}
