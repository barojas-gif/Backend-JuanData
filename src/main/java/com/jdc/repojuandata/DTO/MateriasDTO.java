package com.jdc.repojuandata.DTO;

public class MateriasDTO {
    private Long id_materia;
    private Long id_carrera;
    private Long id_semestre;
    private String nombre_materia;

    public Long getId_materia() {
        return id_materia;
    }

    public void setId_materia(Long id_materia) {
        this.id_materia = id_materia;
    }

    public Long getId_carrera() {
        return id_carrera;
    }

    public void setId_carrera(Long id_carrera) {
        this.id_carrera = id_carrera;
    }

    public Long getId_semestre() {
        return id_semestre;
    }

    public void setId_semestre(Long id_semestre) {
        this.id_semestre = id_semestre;
    }

    public String getNombre_materia() {
        return nombre_materia;
    }

    public void setNombre_materia(String nombre_materia) {
        this.nombre_materia = nombre_materia;
    }
}
