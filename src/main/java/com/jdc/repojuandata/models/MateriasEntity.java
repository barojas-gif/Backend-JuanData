package com.jdc.repojuandata.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table (name = "materias")
@Getter
@Setter
public class MateriasEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia")
    private Long id_materia;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nombre_materia")
    private String nombre_materia;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_carrera", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CarrerasEntity carrerasEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_semestre",  nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SemestresEntity semestresEntity;
}
