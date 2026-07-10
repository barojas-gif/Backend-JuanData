package com.jdc.repojuandata.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class DocumentosDTO {
    @JsonProperty("idDocumento")
    private Long id_documento;
    
    @JsonProperty("idMateria")
    private Long id_materia;

    @JsonProperty("idUsuario")
    private Long id_usuario;

    @JsonProperty("tituloDocumento")
    private String titulo_documento;

    @JsonProperty("temaDocumento")
    private String tema_documento;

    @JsonProperty("fechaDocumento")
    private Date fecha_documento;

    @JsonProperty("archivoDocumento")
    private String archivo_documento;

    @JsonProperty("rutaArchivo")
    private String ruta_archivo;

    @JsonProperty("estado")
    private int estado;

    public Long getId_documento() {
        return id_documento;
    }

    public void setId_documento(Long id_documento) {
        this.id_documento = id_documento;
    }

    public Long getId_materia() {
        return id_materia;
    }

    public void setId_materia(Long id_materia) {
        this.id_materia = id_materia;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTitulo_documento() {
        return titulo_documento;
    }

    public void setTitulo_documento(String titulo_documento) {
        this.titulo_documento = titulo_documento;
    }

    public String getTema_documento() {
        return tema_documento;
    }

    public void setTema_documento(String tema_documento) {
        this.tema_documento = tema_documento;
    }

    public Date getFecha_documento() {
        return fecha_documento;
    }

    public void setFecha_documento(Date fecha_documento) {
        this.fecha_documento = fecha_documento;
    }

    public String getArchivo_documento() {
        return archivo_documento;
    }

    public void setArchivo_documento(String archivo_documento) {
        this.archivo_documento = archivo_documento;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getRuta_archivo() {
        return ruta_archivo;
    }

    public void setRuta_archivo(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
    }
}
