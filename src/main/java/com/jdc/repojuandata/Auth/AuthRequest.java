package com.jdc.repojuandata.Auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String correoUsuario;
    private String contrasenaUsuario;
}