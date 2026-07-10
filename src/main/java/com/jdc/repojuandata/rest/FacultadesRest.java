package com.jdc.repojuandata.rest;


import com.jdc.repojuandata.models.FacultadEntity;
import com.jdc.repojuandata.service.IFacultadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/facultades")
public class FacultadesRest {

    @Autowired
    private IFacultadService ifacultadService;

    @GetMapping("/listar")
    public ResponseEntity<List<FacultadEntity>> listarFacultades() {

        return ResponseEntity.ok(ifacultadService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<FacultadEntity> listarFacultadesById(@PathVariable("id") Long id) {
        FacultadEntity role = ifacultadService.findById(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<FacultadEntity> crearFacultades(@Valid @RequestBody FacultadEntity facultadEntity) {
        try {
            ifacultadService.save(facultadEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(facultadEntity);
        } catch (Exception e) {
            System.err.println("Error al crear la facultad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/deleteby/{id}")
    public ResponseEntity<String> deleteFacultadesById(@PathVariable("id") Long id) {
        try {
            FacultadEntity aux = ifacultadService.findById(id);
            if (aux == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró la facultad con ID: " + id);
            }

            ifacultadService.deleteById(id);
            return ResponseEntity.ok("Facultad eliminada exitosamente con ID: " + id);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al eliminar: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<FacultadEntity> editFacultad(@PathVariable("id") Long id,
                                                 @Valid @RequestBody FacultadEntity facultadEntity) {
        try {
            FacultadEntity facultadExistentes = ifacultadService.findById(id);

            if (facultadExistentes == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            facultadExistentes.setNombreFacultad(facultadEntity.getNombreFacultad());

            ifacultadService.save(facultadExistentes);
            return ResponseEntity.ok(facultadExistentes);
        } catch (Exception e) {
            System.err.println("Error al actualizar la Facultad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
