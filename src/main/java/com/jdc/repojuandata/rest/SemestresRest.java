package com.jdc.repojuandata.rest;

import com.jdc.repojuandata.models.FacultadEntity;
import com.jdc.repojuandata.models.SemestresEntity;
import com.jdc.repojuandata.service.SemestresService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/semestres")
public class SemestresRest {

    @Autowired
    private SemestresService iSemestresService;

    @GetMapping("/listar")
    public ResponseEntity<List<SemestresEntity>> listarSemestres() {

        return ResponseEntity.ok(iSemestresService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<SemestresEntity> listarSemestreById(@PathVariable("id") Long id) {
        SemestresEntity role = iSemestresService.findById(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<SemestresEntity> creaSemestre(@Valid @RequestBody SemestresEntity semestresEntity) {
        try {
            iSemestresService.save(semestresEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(semestresEntity);
        } catch (Exception e) {
            System.err.println("Error al crear el semestre: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/deleteby/{id}")
    public ResponseEntity<String> deleteSemestreById(@PathVariable("id") Long id) {
        try {
            SemestresEntity aux = iSemestresService.findById(id);
            if (aux == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró el semestre con ID: " + id);
            }

            iSemestresService.deleteById(id);
            return ResponseEntity.ok("Semestre eliminado exitosamente con ID: " + id);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al eliminar: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<SemestresEntity> editSemestre(@PathVariable("id") Long id,
                                                       @Valid @RequestBody SemestresEntity semestresEntity) {
        try {
            SemestresEntity semestreExistentes = iSemestresService.findById(id);

            if (semestreExistentes == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            semestreExistentes.setNumero_semestre(semestresEntity.getNumero_semestre());

            iSemestresService.save(semestreExistentes);
            return ResponseEntity.ok(semestreExistentes);
        } catch (Exception e) {
            System.err.println("Error al actualizar la Facultad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/carrera/{id}")
    public ResponseEntity<List<SemestresEntity>> obtenerSemestresPorCarrera(@PathVariable("id") Long id) {
        List<SemestresEntity> lista = iSemestresService.findByCarreraId(id);
        return ResponseEntity.ok(lista);
    }



}
