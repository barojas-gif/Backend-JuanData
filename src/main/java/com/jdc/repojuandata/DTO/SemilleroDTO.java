package com.jdc.repojuandata.DTO;

public class SemilleroDTO {
    private Long id;
    private String nombreSemillero;
    private String facultad;

    public SemilleroDTO(Long id, String nombreSemillero, String facultad) {
        this.id = id;
        this.nombreSemillero = nombreSemillero;
        this.facultad = facultad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreSemillero() {
        return nombreSemillero;
    }

    public void setNombreSemillero(String nombreSemillero) {
        this.nombreSemillero = nombreSemillero;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }
}

