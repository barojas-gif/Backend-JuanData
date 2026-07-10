package com.jdc.repojuandata.Auth;

import lombok.Getter;
import lombok.Setter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String nombreUsuario;
    private String apellidoUsuario;
    private String correoUsuario;
    private String numeroDocumento;
    private String numeroTelefono;
    private Long idCarrera;

}


