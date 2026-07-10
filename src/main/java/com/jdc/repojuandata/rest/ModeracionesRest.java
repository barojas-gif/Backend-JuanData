package com.jdc.repojuandata.rest;

import com.jdc.repojuandata.DTO.ModeracionesDTO;
import com.jdc.repojuandata.models.*;
import com.jdc.repojuandata.service.IDocumentosService;
import com.jdc.repojuandata.service.IModeracionesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/moderaciones")
public class ModeracionesRest {

    @Autowired
    private IModeracionesService iModeracionesService;

    @Autowired
    private IDocumentosService iDocumentosService;

    @GetMapping("/listar")
    public ResponseEntity<List<ModeracionesEntity>> listarModeraciones(){
        return ResponseEntity.ok(iModeracionesService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ModeracionesEntity> listarModeracionesById(@PathVariable("id") Long id){
        ModeracionesEntity moderacionesEntity = iModeracionesService.findById(id);
        if(moderacionesEntity != null){
            return ResponseEntity.ok(moderacionesEntity);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<ModeracionesEntity> crearModeraciones(@Valid @RequestBody ModeracionesDTO moderacionesDTO){
        try {
            // Crear un nuevo Entity basado en los datos del DTO
            ModeracionesEntity moderacionesEntity = new ModeracionesEntity();
            moderacionesEntity.setEstadoModeracion(moderacionesDTO.getEstado_moderacion());
            moderacionesEntity.setObservacionModeracion(moderacionesDTO.getObservacion_moderacion());

            // Verificar la existencia del documento
            DocumentosEntity documentosEntity = iDocumentosService.findById(moderacionesDTO.getId_documento());
            if (documentosEntity == null){
                System.out.println("Docuemnto no encontrado con ID: " + moderacionesDTO.getId_documento());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // Asignar carrera encontrado a la materia
            moderacionesEntity.setDocumentosEntity(documentosEntity);

            // Guardar el usuario en la base de datos
            iModeracionesService.save(moderacionesEntity);

            return ResponseEntity.ok(moderacionesEntity);
        }catch (Exception e){
            System.out.println("Error al crear la materia: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteby/{id}")
    public ResponseEntity<String> deleteModeracionesById(@PathVariable("id") Long id){
        try {
            ModeracionesEntity aux = iModeracionesService.findById(id);
            // CORREGIDO: La lógica estaba invertida
            if(aux == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la moderacion con el id: " + id);
            }
            iModeracionesService.deletebyid(id);
            return ResponseEntity.ok("Moderacion eliminada con el id: " + id);
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar: " + ex.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<ModeracionesEntity> editModeracion(@PathVariable("id") Long id, @Valid @RequestBody ModeracionesDTO moderacionesDTO){
        try {
            //Buscar Materia por ID
            ModeracionesEntity moderacionesExistentes = iModeracionesService.findById(id);

            //Verificar si la moderacion existe
            if(moderacionesExistentes == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            //Actualizar los campos de la entidad existente con los datos del DTO
            moderacionesExistentes.setEstadoModeracion(moderacionesDTO.getEstado_moderacion());
            moderacionesExistentes.setObservacionModeracion(moderacionesDTO.getObservacion_moderacion());

            //Actualizar el documento relacionada (si cambia)
            DocumentosEntity documentosEntity = iDocumentosService.findById(moderacionesDTO.getId_documento());
            if(documentosEntity == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            moderacionesExistentes.setDocumentosEntity(documentosEntity);

            //Guardar los cambios en la entidad existente
            iModeracionesService.save(moderacionesExistentes);

            // Retornar la entidad actualizada
            return ResponseEntity.ok(moderacionesExistentes);
        }catch (Exception e){
            System.out.println("Error al editar la moderacion: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
