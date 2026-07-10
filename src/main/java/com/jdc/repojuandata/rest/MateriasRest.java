package com.jdc.repojuandata.rest;


import com.jdc.repojuandata.DTO.MateriasDTO;
import com.jdc.repojuandata.models.*;
import com.jdc.repojuandata.service.ICarrerasService;
import com.jdc.repojuandata.service.IMateriasService;
import com.jdc.repojuandata.service.ISemestresService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/materias")
public class MateriasRest {

    @Autowired
    private IMateriasService iMateriasService;

    @Autowired
    private ICarrerasService iCarrerasService;

    @Autowired
    private ISemestresService iSemestresService;

    @GetMapping("/listar")
    public ResponseEntity<List<MateriasEntity>> listarMaterias(){
        return ResponseEntity.ok(iMateriasService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<MateriasEntity> listarMateriasById(@PathVariable("id") Long id){
        MateriasEntity materias = iMateriasService.findById(id);
        if(materias != null){
            return ResponseEntity.ok(materias);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<MateriasEntity> crearMaterias(@Valid @RequestBody MateriasDTO materiasDTO){
        try {
            // Crear un nuevo Entity basado en los datos del DTO
            MateriasEntity materiasEntity = new MateriasEntity();
            materiasEntity.setNombre_materia(materiasDTO.getNombre_materia());

            // Verificar la existencia de la carrera
            CarrerasEntity carrerasEntity = iCarrerasService.findById(materiasDTO.getId_carrera());
            if (carrerasEntity == null){
                System.out.println("Carrera no encontrada con ID: " + materiasDTO.getId_carrera());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // Asignar carrera encontrado a la materia
            materiasEntity.setCarrerasEntity(carrerasEntity);

            // NOTA: También necesitas manejar la carrera si es requerida
            SemestresEntity semestresEntity = iSemestresService.findById(materiasDTO.getId_semestre());
            if (semestresEntity == null){
                System.out.println("Semestre no encontrado con ID: " + materiasDTO.getId_semestre());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            materiasEntity.setSemestresEntity(semestresEntity);

            // Guardar el usuario en la base de datos
            iMateriasService.save(materiasEntity);

            return ResponseEntity.ok(materiasEntity);
        }catch (Exception e){
            System.out.println("Error al crear la materia: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteby/{id}")
    public ResponseEntity<String> deleteMateriaById(@PathVariable("id") Long id){
        try {
            MateriasEntity aux = iMateriasService.findById(id);
            // CORREGIDO: La lógica estaba invertida
            if(aux == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la materia con el id: " + id);
            }
            iMateriasService.deleteById(id);
            return ResponseEntity.ok("Materia eliminada con el id: " + id);
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar: " + ex.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<MateriasEntity> editMateria(@PathVariable("id") Long id, @Valid @RequestBody MateriasDTO materiasDTO){
        try {
            //Buscar Materia por ID
            MateriasEntity materiasExistentes = iMateriasService.findById(id);

            //Verificar si el usuario existe
            if(materiasExistentes == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            //Actualizar los campos de la entidad existente con los datos del DTO
            materiasExistentes.setNombre_materia(materiasDTO.getNombre_materia());

            //Actualizar el semestre relacionada (si cambia)
            SemestresEntity semestresEntity = iSemestresService.findById(materiasDTO.getId_semestre());
            if(semestresEntity == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            materiasExistentes.setSemestresEntity(semestresEntity);

            // NOTA: También manejar carrera si es necesario
            CarrerasEntity carrerasEntity = iCarrerasService.findById(materiasDTO.getId_carrera());
            if(carrerasEntity == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            materiasExistentes.setCarrerasEntity(carrerasEntity);

            //Guardar los cambios en la entidad existente
            iMateriasService.save(materiasExistentes);

            // Retornar la entidad actualizada
            return ResponseEntity.ok(materiasExistentes);
        }catch (Exception e){
            System.out.println("Error al editar la materia: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*
    @GetMapping("/porSemestre/{id}")
    public ResponseEntity<List<MateriasEntity>> listarMateriasPorSemestre(@PathVariable("id") Long id) {
        try {
            List<MateriasEntity> materiasPorSemestre = iMateriasService.findBySemestreId(id);

            if (materiasPorSemestre.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(materiasPorSemestre);

        } catch (Exception e) {
            System.out.println("Error al obtener materias por semestre: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/

    // Reemplaza el endpoint actual por uno que use carrera + semestre
    @GetMapping("/porCarreraYSemestre")
    public ResponseEntity<List<MateriasEntity>> listarPorCarreraYSemestre(
            @RequestParam("idCarrera") Long idCarrera,
            @RequestParam("idSemestre") Long idSemestre) {
        try {
            List<MateriasEntity> materias = iMateriasService.findByCarreraAndSemestre(idCarrera, idSemestre);
            return ResponseEntity.ok(materias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
