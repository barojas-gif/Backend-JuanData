package com.jdc.repojuandata.service;

import com.jdc.repojuandata.models.UsuariosEntity;
import com.jdc.repojuandata.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuariosEntity getUsuarioAutenticado() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByCorreoUsuario(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
