package com.jdc.repojuandata.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class UsuariosEntity implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotNull
    @Column(name = "numero_documento")
    private String numeroDocumento;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @NotNull
    @Size(min = 1, max = 150)
    @Column (name = "apellido_usuario")
    private String apellidoUsuario;

    @NotNull
    @Column(name = "numero_telefono")
    private String numeroTelefono;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "correo_usuario", unique = true)
    private String correoUsuario;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "contrasena_usuario")
    private String contrasenaUsuario;

    @NotNull
    @Column(name = "temporal_contrasena")
    private boolean temporalContrasena = false;

    @NotNull
    @Column(name = "estado")
    private int estado; // 1 = activo, 0 = eliminado/inactivo

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RolesEntity rolesEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_carrera", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CarrerasEntity carrerasEntity;

    // Este método es crucial para que Spring Security sepa qué rol tiene el usuario
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String rol = "ROLE_" + rolesEntity.getNombreRol().toUpperCase(); // Ej: ROLE_ADMINISTRADOR o ROLE_ESTUDIANTE
        return Collections.singletonList(new SimpleGrantedAuthority(rol));
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_semillero") // puede ser null
    private SemilleroEntity semillero;

// borrar lo de semilleros------------------------------------------------------

    @Override
    public String getPassword() {
        return this.contrasenaUsuario;
    }

    @Override
    public String getUsername() {
        return this.correoUsuario; // usamos el correo como nombre de usuario
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
