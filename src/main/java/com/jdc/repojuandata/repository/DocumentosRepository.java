package com.jdc.repojuandata.repository;

import com.jdc.repojuandata.DTO.DocumentoMasVistoDTO;
import com.jdc.repojuandata.DTO.DocumentoSemilleroDTO;
import com.jdc.repojuandata.models.DocumentosEntity;
import com.jdc.repojuandata.models.SemilleroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentosRepository extends JpaRepository <DocumentosEntity, Long> {

    // Consulta para mostrar los documentos por materia
    @Query("SELECT d FROM DocumentosEntity d WHERE d.materiasEntity.id_materia = :idMateria ORDER BY d.fechaDocumento DESC")
    List<DocumentosEntity> findByMateriaId(@Param("idMateria") Long idMateria);

    // Cambiado a List<> para evitar NonUniqueResultException
    List<DocumentosEntity> findByArchivoDocumento(String archivoDocumento);
    List<DocumentosEntity> findBySemillero_Id(Long idSemillero);


    @Query("""
    SELECT new com.jdc.repojuandata.DTO.DocumentoMasVistoDTO(
        u.nombreUsuario, 
        d.archivoDocumento, 
        d.materiasEntity.semestresEntity.numero_semestre,
        MAX(v.fechaVista), 
        COUNT(v)
    )
    FROM VistaEntity v
    JOIN v.documento d
    JOIN v.usuario u
    GROUP BY u.nombreUsuario, d.archivoDocumento, d.materiasEntity.semestresEntity.numero_semestre
    ORDER BY COUNT(v) DESC
""")
    List<DocumentoMasVistoDTO> obtenerDocumentosMasVistos();




    @Query("SELECT d FROM DocumentosEntity d WHERE d.materiasEntity.id_materia = :idMateria AND d.estado = 1")
    List<DocumentosEntity> findAceptadosByMateria(@Param("idMateria") Long id);


    @Query("SELECT d FROM DocumentosEntity d " +
            "JOIN FETCH d.usuariosEntity " +
            "JOIN FETCH d.materiasEntity m " +
            "JOIN FETCH m.semestresEntity " + // si necesitas también semestre
            "WHERE d.estado = :estado")
    List<DocumentosEntity> findByEstado(@Param("estado") int estado);

    @Query("""
    SELECT d FROM DocumentosEntity d 
    WHERE d.estado = :estado AND d.materiasEntity.carrerasEntity.idCarrera = :idCarrera
""")
    List<DocumentosEntity> findByEstadoAndCarrera(@Param("estado") int estado, @Param("idCarrera") Long idCarrera);

    @Query("SELECT COUNT(d) FROM DocumentosEntity d WHERE d.estado = :estado AND d.materiasEntity.carrerasEntity.idCarrera = :idCarrera")
    Long countByEstadoAndCarrera(@Param("estado") int estado, @Param("idCarrera") Long idCarrera);

    @Query("""
    SELECT new com.jdc.repojuandata.DTO.DocumentoSemilleroDTO(
        u.idUsuario,
        u.nombreUsuario,
        u.apellidoUsuario,
        s.id,
        s.nombre,
        c.idCarrera,
        c.nombreCarrera,
        d.idDocumento,
        d.tituloDocumento,
        d.temaDocumento,
        d.archivoDocumento,
        d.fechaDocumento
    )
    FROM UsuariosEntity u
    JOIN u.semillero s
    JOIN s.carrera c
    JOIN s.documentos d
    WHERE u.idUsuario = :idUsuario
""")
    List<DocumentoSemilleroDTO> obtenerDocumentosPorUsuario(@Param("idUsuario") Long idUsuario);
    List<DocumentosEntity> findBySemillero(SemilleroEntity semillero);


}

