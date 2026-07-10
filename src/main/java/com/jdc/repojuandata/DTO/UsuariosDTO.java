package com.jdc.repojuandata.DTO;

public class UsuariosDTO {

    private Long id_usuario;
    private Long id_rol;
    private Long id_carrera;
    private Long id_semillero;
    private String nombre_usuario;
    private String apellido_usuario;
    private String correo_usuario;
    private String contrasena_usuario;
    private String telefono_usuario;
    private String documento_usuario;

    public Long getId_usuario() {
        return id_usuario;
    }




    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getApellido_usuario() {
        return apellido_usuario;
    }

    public void setApellido_usuario(String apellido_usuario) {
        this.apellido_usuario = apellido_usuario;
    }

    public Long getId_rol() {
        return id_rol;
    }

    public void setId_rol(Long id_rol) {
        this.id_rol = id_rol;
    }

    public Long getId_carrera() {
        return id_carrera;
    }

    public void setId_carrera(Long id_carrera) {
        this.id_carrera = id_carrera;
    }

    public Long getId_semillero() {
        return id_semillero;
    }

    public void setId_semillero(Long id_semillero) {
        this.id_semillero = id_semillero;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getCorreo_usuario() {
        return correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }

    public String getContrasena_usuario() {
        return contrasena_usuario;
    }

    public void setContrasena_usuario(String contrasena_usuario) {
        this.contrasena_usuario = contrasena_usuario;
    }

    public String getTelefono_usuario() {
        return telefono_usuario;
    }

    public void setTelefono_usuario(String telefono_usuario) {
        this.telefono_usuario = telefono_usuario;
    }

    public String getDocumento_usuario() {
        return documento_usuario;
    }

    public void setDocumento_usuario(String documento_usuario) {
        this.documento_usuario = documento_usuario;
    }
}
