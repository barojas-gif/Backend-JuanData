package com.jdc.repojuandata.rest;

import com.jdc.repojuandata.models.RolesEntity;
import com.jdc.repojuandata.service.RolesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/roles")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class RolesRest {

    @Autowired
    private RolesService iRolesService;

    @GetMapping("/listar")
    public ResponseEntity<List<RolesEntity>> listarRoles() {

        return ResponseEntity.ok(iRolesService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<RolesEntity> listarRolesById(@PathVariable("id") Long id) {
        RolesEntity role = iRolesService.findById(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<RolesEntity> crearRoles(@Valid @RequestBody RolesEntity rolesEntity) {
        try {
            iRolesService.save(rolesEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(rolesEntity);
        } catch (Exception e) {
            System.err.println("Error al crear rol: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/deleteby/{id}")
    public ResponseEntity<String> deleteRolesById(@PathVariable("id") Long id) {
        try {
            RolesEntity aux = iRolesService.findById(id);
            if (aux == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró el rol con ID: " + id);
            }

            iRolesService.deleteById(id);
            return ResponseEntity.ok("Rol eliminado exitosamente con ID: " + id);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al eliminar: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<RolesEntity> editRoles(@PathVariable("id") Long id,
                                                 @Valid @RequestBody RolesEntity rolesEntity) {
        try {
            RolesEntity rolesExistentes = iRolesService.findById(id);

            if (rolesExistentes == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            rolesExistentes.setNombreRol(rolesEntity.getNombreRol());
            rolesExistentes.setEstadoRol(rolesEntity.getEstadoRol());

            iRolesService.save(rolesExistentes);
            return ResponseEntity.ok(rolesExistentes);
        } catch (Exception e) {
            System.err.println("Error al actualizar rol: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}