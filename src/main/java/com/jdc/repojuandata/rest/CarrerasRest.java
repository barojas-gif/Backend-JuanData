package com.jdc.repojuandata.rest;

import com.jdc.repojuandata.DTO.CarrerasDTO;
import com.jdc.repojuandata.models.CarrerasEntity;
import com.jdc.repojuandata.models.FacultadEntity;
import com.jdc.repojuandata.service.CarrerasService;
import com.jdc.repojuandata.service.FacultadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/carreras") 
public class CarrerasRest {

    @Autowired
    private CarrerasService icarrerasService;

    @Autowired
    private FacultadService iFacultadService;

    @GetMapping("/listar")
    public ResponseEntity<List<CarrerasEntity>> listarCarreras(){
        return ResponseEntity.ok(icarrerasService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CarrerasEntity> listarCarrerasById(@PathVariable("id") Long id){
        CarrerasEntity usuario = icarrerasService.findById(id);
        if(usuario != null){
            return ResponseEntity.ok(usuario);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<CarrerasEntity> crearCarreras(@Valid @RequestBody CarrerasDTO carrerasDTO){
        try {
            // Crear un nuevo Entity basado en los datos del DTO
            CarrerasEntity carrerasEntity = new CarrerasEntity();
            carrerasEntity.setNombreCarrera(carrerasDTO.getNombre_carrera());

            // Verificar la existencia de la facultad
            FacultadEntity facultadEntity = iFacultadService.findById(carrerasDTO.getId_facultad());
            if (facultadEntity == null){
                System.out.println("Facultad no encontrada con ID: " + carrerasDTO.getId_facultad());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // Asignar facultad encontrado a la carrera
            carrerasEntity.setFacultad(facultadEntity);


            // Guardar la carrera en la base de datos
            icarrerasService.save(carrerasEntity);

            return ResponseEntity.ok(carrerasEntity);
        }catch (Exception e){
            System.out.println("Error al crear la carrera: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteby/{id}")
    public ResponseEntity<String> deleteCarreraById(@PathVariable("id") Long id){
        try {
            CarrerasEntity aux = icarrerasService.findById(id);
            // CORREGIDO: La lógica estaba invertida
            if(aux == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la carrera con el id: " + id);
            }
            icarrerasService.deleteById(id);
            return ResponseEntity.ok("Carrera eliminada con el id: " + id);
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar: " + ex.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CarrerasEntity> editCarreras(@PathVariable("id") Long id, @Valid @RequestBody CarrerasDTO carrerasDTO){
        try {
            //Buscar Carrera por ID
            CarrerasEntity carrerasExistentes = icarrerasService.findById(id);

            //Verificar si la carrera existe
            if(carrerasExistentes == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            //Actualizar los campos de la entidad existente con los datos del DTO
            carrerasExistentes.setNombreCarrera(carrerasDTO.getNombre_carrera());

            //Actualizar la Facultad relacionado (si cambia)
            FacultadEntity facultadEntity = iFacultadService.findById(carrerasDTO.getId_facultad());
            if(facultadEntity == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            carrerasExistentes.setFacultad(facultadEntity);



            //Guardar los cambios en la entidad existente
            icarrerasService.save(carrerasExistentes);

            // Retornar la entidad actualizada
            return ResponseEntity.ok(carrerasExistentes);
        }catch (Exception e){
            System.out.println("Error al editar el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
