package com.jdc.repojuandata.DTO;

public class ModeracionesDTO {
    private Long id_moderacion;
    private Long id_documento;
    private int estado_moderacion;
    private String observacion_moderacion;

    public Long getId_moderacion() {
        return id_moderacion;
    }

    public void setId_moderacion(Long id_moderacion) {
        this.id_moderacion = id_moderacion;
    }

    public Long getId_documento() {
        return id_documento;
    }

    public void setId_documento(Long id_documento) {
        this.id_documento = id_documento;
    }

    public int getEstado_moderacion() {
        return estado_moderacion;
    }

    public void setEstado_moderacion(int estado_moderacion) {
        this.estado_moderacion = estado_moderacion;
    }

    public String getObservacion_moderacion() {
        return observacion_moderacion;
    }

    public void setObservacion_moderacion(String observacion_moderacion) {
        this.observacion_moderacion = observacion_moderacion;
    }
}
