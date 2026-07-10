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
@Table(name = "moderaciones")
@Getter
@Setter
public class ModeracionesEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moderacion")
    private Long idModeracion;

    @NotNull
    @Column(name = "estado_moderacion")
    private int estadoModeracion;

    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "observacion_moderacion")
    private String observacionModeracion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_documento", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DocumentosEntity documentosEntity;
}
