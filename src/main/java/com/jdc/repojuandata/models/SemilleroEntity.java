package com.jdc.repojuandata.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "semilleros")
public class SemilleroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_semillero")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "semillero")
    private List<UsuariosEntity> usuarios;

    @OneToMany(mappedBy = "semillero")
    private List<DocumentosEntity> documentos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_carrera", nullable = false)
    private CarrerasEntity carrera;

    // --- GETTERS Y SETTERS CORREGIDOS PARA JACKSON ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @JsonIgnore // 👈 Agrégalo aquí también para asegurar que no se dispare la recursión
    public List<UsuariosEntity> getUsuarios() { return usuarios; }
    public void setUsuarios(List<UsuariosEntity> usuarios) { this.usuarios = usuarios; }

    @JsonIgnore // 👈 Agrégalo aquí también
    public List<DocumentosEntity> getDocumentos() { return documentos; }
    public void setDocumentos(List<DocumentosEntity> documentos) { this.documentos = documentos; }

    public CarrerasEntity getCarrera() { return carrera; }
    public void setCarrera(CarrerasEntity carrera) { this.carrera = carrera; }
}