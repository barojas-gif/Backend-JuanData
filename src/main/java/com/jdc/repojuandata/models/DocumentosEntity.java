package com.jdc.repojuandata.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "documentos")
public class DocumentosEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Long idDocumento;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "titulo_documento")
    private String tituloDocumento;

    @NotNull
    @Size(min = 1, max = 150)
    @Column (name = "tema_documento")
    private String temaDocumento;


    @Size(min = 1, max = 500)
    @Column (name = "ruta_archivo")
    private String rutaArchivo;

    @NotNull
    @Column(name = "fecha_documento")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocumento;

    @NotNull
    @Size(min = 1, max = 255)
    @Column (name = "archivo_documento")
    private String archivoDocumento;

    @Size(min = 1, max = 255)
    @Column (name = "categoria")
    private String categoria;

    @NotNull
    @Column(name ="estado")
    private int estado; // 0 = pendiente, 1 = aceptado, 2 = rechazado (opcional)


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UsuariosEntity usuariosEntity;

    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MateriasEntity materiasEntity;


    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_semillero", referencedColumnName = "id_semillero")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SemilleroEntity semillero;


    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public @NotNull @Size(min = 1, max = 150) String getTituloDocumento() {
        return tituloDocumento;
    }

    public void setTituloDocumento(@NotNull @Size(min = 1, max = 150) String tituloDocumento) {
        this.tituloDocumento = tituloDocumento;
    }

    public @NotNull @Size(min = 1, max = 150) String getTemaDocumento() {
        return temaDocumento;
    }

    public void setTemaDocumento(@NotNull @Size(min = 1, max = 150) String temaDocumento) {
        this.temaDocumento = temaDocumento;
    }

    public @Size(min = 1, max = 500) String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(@Size(min = 1, max = 500) String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public @NotNull Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(@NotNull Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public @NotNull @Size(min = 1, max = 255) String getArchivoDocumento() {
        return archivoDocumento;
    }

    public void setArchivoDocumento(@NotNull @Size(min = 1, max = 255) String archivoDocumento) {
        this.archivoDocumento = archivoDocumento;
    }

    public @Size(min = 1, max = 255) String getCategoria() {
        return categoria;
    }

    public void setCategoria(@Size(min = 1, max = 255) String categoria) {
        this.categoria = categoria;
    }

    @NotNull
    public int getEstado() {
        return estado;
    }

    public void setEstado(@NotNull int estado) {
        this.estado = estado;
    }

    public @NotNull UsuariosEntity getUsuariosEntity() {
        return usuariosEntity;
    }

    public void setUsuariosEntity(@NotNull UsuariosEntity usuariosEntity) {
        this.usuariosEntity = usuariosEntity;
    }

    public MateriasEntity getMateriasEntity() {
        return materiasEntity;
    }

    public void setMateriasEntity(MateriasEntity materiasEntity) {
        this.materiasEntity = materiasEntity;
    }

    public SemilleroEntity getSemillero() {
        return semillero;
    }

    public void setSemillero(SemilleroEntity semillero) {
        this.semillero = semillero;
    }
}
