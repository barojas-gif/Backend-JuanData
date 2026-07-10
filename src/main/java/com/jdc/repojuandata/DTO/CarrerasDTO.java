package com.jdc.repojuandata.DTO;

public class CarrerasDTO {
    private Long id_carrera;
    private Long id_facultad;
    private String nombre_carrera;

    public Long getId_carrera() {
        return id_carrera;
    }

    public void setId_carrera(Long id_carrera) {
        this.id_carrera = id_carrera;
    }

    public Long getId_facultad() {
        return id_facultad;
    }

    public void setId_facultad(Long id_facultad) {
        this.id_facultad = id_facultad;
    }

    public String getNombre_carrera() {
        return nombre_carrera;
    }

    public void setNombre_carrera(String nombre_carrera) {
        this.nombre_carrera = nombre_carrera;
    }
}
