package com.jdc.repojuandata.service;

import com.jdc.repojuandata.DTO.DocumentoMasVistoDTO;
import com.jdc.repojuandata.models.DocumentosEntity;
import com.jdc.repojuandata.repository.DocumentosRepository;

import java.util.List;

public interface IDocumentosService {
    public List<DocumentosEntity> findAll();
    public DocumentosEntity findById(Long id);
    public void save(DocumentosEntity documentosEntity);
    public void deleteById(Long id);
    List<DocumentosEntity> findByMateriaId(Long id);
    List<DocumentosEntity> findByArchivoNombre(String archivoDocumento);
    List<DocumentoMasVistoDTO> obtenerDocumentosMasVistos();
    List<DocumentosEntity> findByEstado(int estado);

    void actualizarEstadoYEnviarCorreo(Long id, int estado, String mensaje, String correoEstudiante);


    List<DocumentosEntity> findBySemillero(Long idSemillero);
    List<DocumentosEntity> findBySemilleroDelUsuarioAutenticado();

}
