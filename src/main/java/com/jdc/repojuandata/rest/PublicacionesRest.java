package com.jdc.repojuandata.rest;

import com.jdc.repojuandata.DTO.MateriasDTO;
import com.jdc.repojuandata.DTO.PublicacionesDTO;
import com.jdc.repojuandata.models.*;
import com.jdc.repojuandata.service.CarrerasService;
import com.jdc.repojuandata.service.DocumentosService;
import com.jdc.repojuandata.service.MateriasService;
import com.jdc.repojuandata.service.PublicacionesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/publicaciones")
public class PublicacionesRest {

    @Autowired
    private PublicacionesService iPublicacionesService;

    @Autowired
    private CarrerasService iCarrerasService;

    @Autowired
    private MateriasService iMateriasService;

    @Autowired
    private DocumentosService iDocumentosService;

    @GetMapping("/listar")
    public ResponseEntity<List<MateriasEntity>> listarPublicaciones(){
        return ResponseEntity.ok(iMateriasService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<PublicacionesEntity> listarPublicacionesById(@PathVariable("id") Long id){
        PublicacionesEntity publicacionesEntity = iPublicacionesService.findById(id);
        if(publicacionesEntity != null){
            return ResponseEntity.ok(publicacionesEntity);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<PublicacionesEntity> crearPublicaciones(@Valid @RequestBody PublicacionesDTO publicacionesDTO){
        try {
            // Crear un nuevo Entity basado en los datos del DTO
            PublicacionesEntity publicacionesEntity = new PublicacionesEntity();

            // Verificar la existencia de la carrera
            CarrerasEntity carrerasEntity = iCarrerasService.findById(publicacionesDTO.getId_carrera());
            if (carrerasEntity == null){
                System.out.println("Carrera no encontrada con ID: " + publicacionesDTO.getId_carrera());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // Asignar carrera encontrado a la materia
            publicacionesEntity.setCarrera(carrerasEntity);

            // NOTA: También necesitas manejar la carrera si es requerida
            MateriasEntity materiasEntity = iMateriasService.findById(publicacionesDTO.getId_materia());
            if (materiasEntity == null){
                System.out.println("Materia no encontrada con ID: " + publicacionesDTO.getId_materia());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            publicacionesEntity.setMateria(materiasEntity);

            // Verificar la existencia del documento
            DocumentosEntity documentosEntity = iDocumentosService.findById(publicacionesDTO.getId_documento());
            if (documentosEntity == null){
                System.out.println("Docuemnto no encontrado con ID: " + publicacionesDTO.getId_documento());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // Asignar carrera encontrado a la materia
            publicacionesEntity.setDocumento(documentosEntity);

            // Guardar el usuario en la base de datos
            iPublicacionesService.save(publicacionesEntity);

            return ResponseEntity.ok(publicacionesEntity);
        }catch (Exception e){
            System.out.println("Error al crear la publicacion: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteby/{id}")
    public ResponseEntity<String> deletePublicacionById(@PathVariable("id") Long id){
        try {
            PublicacionesEntity aux = iPublicacionesService.findById(id);
            // CORREGIDO: La lógica estaba invertida
            if(aux == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la publicacion con el id: " + id);
            }
            iPublicacionesService.deleteById(id);
            return ResponseEntity.ok("Publicacion eliminada con el id: " + id);
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar: " + ex.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PublicacionesEntity> editPublicacion(@PathVariable("id") Long id, @Valid @RequestBody PublicacionesDTO publicacionesDTO){
        try {
            //Buscar Materia por ID
            PublicacionesEntity publicacionesExistentes = iPublicacionesService.findById(id);

            //Verificar si el usuario existe
            if(publicacionesExistentes == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            //Actualizar la materia relacionada (si cambia)
            MateriasEntity materiasEntity = iMateriasService.findById(publicacionesDTO.getId_materia());
            if(materiasEntity == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            publicacionesExistentes.setMateria(materiasEntity);

            // NOTA: También manejar carrera si es necesario
            CarrerasEntity carrerasEntity = iCarrerasService.findById(publicacionesDTO.getId_carrera());
            if(carrerasEntity == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            publicacionesExistentes.setCarrera(carrerasEntity);

            //Actualizar el documento relacionada (si cambia)
            DocumentosEntity documentosEntity = iDocumentosService.findById(publicacionesDTO.getId_documento());
            if(documentosEntity == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            publicacionesExistentes.setDocumento(documentosEntity);

            //Guardar los cambios en la entidad existente
            iPublicacionesService.save(publicacionesExistentes);

            // Retornar la entidad actualizada
            return ResponseEntity.ok(publicacionesExistentes);
        }catch (Exception e){
            System.out.println("Error al editar la publicacion: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
