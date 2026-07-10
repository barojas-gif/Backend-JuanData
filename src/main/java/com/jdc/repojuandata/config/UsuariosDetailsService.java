package com.jdc.repojuandata.config;

import com.jdc.repojuandata.models.UsuariosEntity;
import com.jdc.repojuandata.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UsuariosDetailsService implements UserDetailsService {

    private final UsuarioRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuariosEntity usuario = usuariosRepository.findByCorreoUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Agregamos el prefijo ROLE_ al rol que viene de la base de datos
        String roleName = usuario.getRolesEntity().getNombreRol(); // Ej: Estudiante, Administrador
        String prefixedRole = "ROLE_" + roleName;



        return new User(
                usuario.getCorreoUsuario(),
                usuario.getContrasenaUsuario(),
                Collections.singletonList(new SimpleGrantedAuthority(prefixedRole))
        );
    }
}
