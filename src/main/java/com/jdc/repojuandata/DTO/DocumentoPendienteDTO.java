package com.jdc.repojuandata.DTO;

import java.util.Date;

public class DocumentoPendienteDTO {
    private Long idDocumento;
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private Date fechaDocumento;
    private String temaDocumento;
    private String tituloDocumento;
    private String archivoDocumento;
    private String correoEstudiante;
    private int numeroSemestre;

    public DocumentoPendienteDTO(Long idDocumento, String nombreEstudiante, String apellidoEstudiante, Date fechaDocumento, String temaDocumento, String tituloDocumento, String archivoDocumento, String correoEstudiante, int numeroSemestre) {
        this.idDocumento = idDocumento;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
        this.fechaDocumento = fechaDocumento;
        this.temaDocumento = temaDocumento;
        this.tituloDocumento = tituloDocumento;
        this.archivoDocumento = archivoDocumento;
        this.correoEstudiante = correoEstudiante;
        this.numeroSemestre = numeroSemestre;
    }

    // Getters y setters aquí (o usa Lombok)


    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getArchivoDocumento() {
        return archivoDocumento;
    }

    public void setArchivoDocumento(String archivoDocumento) {
        this.archivoDocumento = archivoDocumento;
    }

    public String getCorreoEstudiante() {
        return correoEstudiante;
    }

    public void setCorreoEstudiante(String correoEstudiante) {
        this.correoEstudiante = correoEstudiante;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getApellidoEstudiante() {
        return apellidoEstudiante;
    }

    public void setApellidoEstudiante(String apellidoEstuduante) {
        this.apellidoEstudiante = apellidoEstuduante;
    }

    public String getTemaDocumento() {
        return temaDocumento;
    }

    public void setTemaDocumento(String temaDocumento) {
        this.temaDocumento = temaDocumento;
    }

    public String getTituloDocumento() {
        return tituloDocumento;
    }

    public void setTituloDocumento(String tituloDocumento) {
        this.tituloDocumento = tituloDocumento;
    }

    public int getNumeroSemestre() {
        return numeroSemestre;
    }

    public void setNumeroSemestre(int numeroSemestre) {
        this.numeroSemestre = numeroSemestre;
    }
}
