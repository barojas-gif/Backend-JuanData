package com.jdc.repojuandata.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "semestres")
@Getter
@Setter
public class SemestresEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_semestre")
    private Long id_semestre;

    @NotNull
    @Column(name = "numero_semestre")
    private int numero_semestre;
}
