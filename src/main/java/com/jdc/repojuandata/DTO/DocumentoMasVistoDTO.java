package com.jdc.repojuandata.DTO;

import java.time.LocalDateTime;

public class DocumentoMasVistoDTO {

    private String nombreEstudiante;
    private String archivoDocumento;
    private Integer semestre;
    private LocalDateTime fechaUltimaVista;
    private Long cantidadVistas;

    // Constructor requerido por el SELECT NEW de JPQL
    public DocumentoMasVistoDTO(String nombreEstudiante, String archivoDocumento, Integer semestre, LocalDateTime fechaUltimaVista, Long cantidadVistas) {
        this.nombreEstudiante = nombreEstudiante;
        this.archivoDocumento = archivoDocumento;
        this.semestre = semestre;
        this.fechaUltimaVista = fechaUltimaVista;
        this.cantidadVistas = cantidadVistas;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getArchivoDocumento() {
        return archivoDocumento;
    }

    public void setArchivoDocumento(String archivoDocumento) {
        this.archivoDocumento = archivoDocumento;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public LocalDateTime getFechaUltimaVista() {
        return fechaUltimaVista;
    }

    public void setFechaUltimaVista(LocalDateTime fechaUltimaVista) {
        this.fechaUltimaVista = fechaUltimaVista;
    }

    public Long getCantidadVistas() {
        return cantidadVistas;
    }

    public void setCantidadVistas(Long cantidadVistas) {
        this.cantidadVistas = cantidadVistas;
    }
}
