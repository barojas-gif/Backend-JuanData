package com.jdc.repojuandata.DTO;

import java.util.Date;

public class DocumentoSemilleroDTO {

    private Long idUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;
    private Long idSemillero;
    private String nombreSemillero;
    private Long idCarrera;
    private String nombreCarrera;
    private Long idDocumento;
    private String tituloDocumento;
    private String temaDocumento;
    private String archivoDocumento;
    private Date fechaDocumento;

    // Constructor
    public DocumentoSemilleroDTO(Long idUsuario, String nombreUsuario, String apellidoUsuario,
                                 Long idSemillero, String nombreSemillero,
                                 Long idCarrera, String nombreCarrera,
                                 Long idDocumento, String tituloDocumento,
                                 String temaDocumento, String archivoDocumento,
                                 Date fechaDocumento) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.idSemillero = idSemillero;
        this.nombreSemillero = nombreSemillero;
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
        this.idDocumento = idDocumento;
        this.tituloDocumento = tituloDocumento;
        this.temaDocumento = temaDocumento;
        this.archivoDocumento = archivoDocumento;
        this.fechaDocumento = fechaDocumento;
    }

    // Getters y Setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getApellidoUsuario() { return apellidoUsuario; }
    public void setApellidoUsuario(String apellidoUsuario) { this.apellidoUsuario = apellidoUsuario; }

    public Long getIdSemillero() { return idSemillero; }
    public void setIdSemillero(Long idSemillero) { this.idSemillero = idSemillero; }

    public String getNombreSemillero() { return nombreSemillero; }
    public void setNombreSemillero(String nombreSemillero) { this.nombreSemillero = nombreSemillero; }

    public Long getIdCarrera() { return idCarrera; }
    public void setIdCarrera(Long idCarrera) { this.idCarrera = idCarrera; }

    public String getNombreCarrera() { return nombreCarrera; }
    public void setNombreCarrera(String nombreCarrera) { this.nombreCarrera = nombreCarrera; }

    public Long getIdDocumento() { return idDocumento; }
    public void setIdDocumento(Long idDocumento) { this.idDocumento = idDocumento; }

    public String getTituloDocumento() { return tituloDocumento; }
    public void setTituloDocumento(String tituloDocumento) { this.tituloDocumento = tituloDocumento; }

    public String getTemaDocumento() { return temaDocumento; }
    public void setTemaDocumento(String temaDocumento) { this.temaDocumento = temaDocumento; }

    public String getArchivoDocumento() { return archivoDocumento; }
    public void setArchivoDocumento(String archivoDocumento) { this.archivoDocumento = archivoDocumento; }

    public Date getFechaDocumento() { return fechaDocumento; }
    public void setFechaDocumento(Date fechaDocumento) { this.fechaDocumento = fechaDocumento; }

}
