package com.jdc.repojuandata.rest;

import com.jdc.repojuandata.models.CarrerasEntity;
import com.jdc.repojuandata.models.SemilleroEntity;
import com.jdc.repojuandata.repository.CarrerasRepository;
import com.jdc.repojuandata.service.ISemilleroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semilleros")
//@CrossOrigin(origins = "*")
public class SemilleroRest {

    
    @Autowired
    private ISemilleroService semilleroService;

    @Autowired
    private CarrerasRepository carrerasRepository;

    // Obtener todos los semilleros
    @GetMapping
    public ResponseEntity<List<SemilleroEntity>> obtenerTodos() {
        return ResponseEntity.ok(semilleroService.findAll());
    }

    // Obtener un semillero por su ID
    @GetMapping("/{id}")
    public ResponseEntity<SemilleroEntity> obtenerPorId(@PathVariable Long id) {
        SemilleroEntity semillero = semilleroService.findById(id);
        return semillero != null ? ResponseEntity.ok(semillero) : ResponseEntity.notFound().build();
    }

    // Crear un semillero
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody SemilleroEntity semilleroEntity) {
        if (semilleroEntity.getCarrera() == null || semilleroEntity.getCarrera().getIdCarrera() == null) {
            return ResponseEntity.badRequest().body("Debe especificar la carrera a la que pertenece el semillero.");
        }

        CarrerasEntity carrera = carrerasRepository.findById(semilleroEntity.getCarrera().getIdCarrera())
                .orElse(null);

        if (carrera == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrera no encontrada.");
        }

        semilleroEntity.setCarrera(carrera);
        semilleroService.save(semilleroEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(semilleroEntity);
    }

    // Actualizar un semillero
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody SemilleroEntity semilleroEntity) {
        SemilleroEntity existente = semilleroService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        existente.setNombre(semilleroEntity.getNombre());

        if (semilleroEntity.getCarrera() != null && semilleroEntity.getCarrera().getIdCarrera() != null) {
            CarrerasEntity carrera = carrerasRepository.findById(semilleroEntity.getCarrera().getIdCarrera())
                    .orElse(null);
            if (carrera == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrera no encontrada.");
            }
            existente.setCarrera(carrera);
        }

        semilleroService.save(existente);
        return ResponseEntity.ok(existente);
    }

    // Eliminar un semillero
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        SemilleroEntity existente = semilleroService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        semilleroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener semilleros por carrera
    @GetMapping("/carrera/{idCarrera}")
    public ResponseEntity<List<SemilleroEntity>> obtenerPorCarrera(@PathVariable Long idCarrera) {
        List<SemilleroEntity> lista = semilleroService.findByCarreraId(idCarrera);
        return ResponseEntity.ok(lista);
    }
}
