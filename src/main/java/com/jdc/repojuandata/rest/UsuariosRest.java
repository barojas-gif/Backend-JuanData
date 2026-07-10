package com.jdc.repojuandata.rest;

import com.jdc.repojuandata.DTO.UsuarioSimpleDTO;
import com.jdc.repojuandata.DTO.UsuariosDTO;
import com.jdc.repojuandata.models.*;
import com.jdc.repojuandata.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/usuarios")
public class UsuariosRest {
    @Autowired
    private IUsuariosService iUsuariosService;

    @Autowired
    private IRolesService iRolesService;

    @Autowired
    private ICarrerasService iCarrerasService;

    @Autowired
    private ISemestresService iSemestresService;
    @Autowired
    private UsuariosServiceImplement usuariosServiceImplement;

    @Autowired
    private ISemilleroService iSemilleroService;

    @GetMapping("/findById/{id}")
    public ResponseEntity<UsuariosEntity> listarUsuariosById(@PathVariable("id") Long id){
        UsuariosEntity usuario = iUsuariosService.findById(id);
        if(usuario != null){
            return ResponseEntity.ok(usuario);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuariosDTO usuariosDTO) {
        try {
            // Verificar existencia del Rol
            RolesEntity rolesEntity = iRolesService.findById(usuariosDTO.getId_rol());
            if (rolesEntity == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Rol no encontrado con ID: " + usuariosDTO.getId_rol());
            }

            // Verificar existencia de la Carrera
            CarrerasEntity carrerasEntity = iCarrerasService.findById(usuariosDTO.getId_carrera());
            if (carrerasEntity == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Carrera no encontrada con ID: " + usuariosDTO.getId_carrera());
            }

            // Crear entidad desde DTO
            UsuariosEntity usuariosEntity = new UsuariosEntity();
            usuariosEntity.setNombreUsuario(usuariosDTO.getNombre_usuario());
            usuariosEntity.setApellidoUsuario(usuariosDTO.getApellido_usuario());
            usuariosEntity.setCorreoUsuario(usuariosDTO.getCorreo_usuario());
            usuariosEntity.setContrasenaUsuario(usuariosDTO.getContrasena_usuario());
            usuariosEntity.setNumeroTelefono(usuariosDTO.getTelefono_usuario());
            usuariosEntity.setNumeroDocumento(usuariosDTO.getDocumento_usuario());
            usuariosEntity.setTemporalContrasena(false);
            usuariosEntity.setEstado(1); // Activo
            usuariosEntity.setRolesEntity(rolesEntity);
            usuariosEntity.setCarrerasEntity(carrerasEntity);

            // Guardar usuario
            UsuariosEntity usuarioCreado = iUsuariosService.save(usuariosEntity);

            // Opcional: retornar un DTO o solo un mensaje
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuario creado exitosamente con ID: " + usuarioCreado.getIdUsuario());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al crear el usuario: " + e.getMessage());
        }
    }



    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Map<String, Object>> desactivarUsuario(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            UsuariosEntity usuario = iUsuariosService.findById(id);
            if (usuario == null) {
                response.put("mensaje", "No se encontró el usuario con el id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            usuario.setEstado(0); // marcar como inactivo
            iUsuariosService.save(usuario);

            response.put("mensaje", "Usuario desactivado con el id: " + id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @PutMapping("/edit/{id}")
    public ResponseEntity<UsuariosEntity> editUsuario(@PathVariable("id") Long id, @Valid @RequestBody UsuariosDTO usuariosDTO){
        try {
            UsuariosEntity usuariosExistentes = iUsuariosService.findById(id);

            if (usuariosExistentes == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // 1. Actualizar campos básicos
            usuariosExistentes.setNombreUsuario(usuariosDTO.getNombre_usuario());
            usuariosExistentes.setApellidoUsuario(usuariosDTO.getApellido_usuario());
            usuariosExistentes.setCorreoUsuario(usuariosDTO.getCorreo_usuario());
            usuariosExistentes.setNumeroTelefono(usuariosDTO.getTelefono_usuario());
            usuariosExistentes.setNumeroDocumento(usuariosDTO.getDocumento_usuario());

            // 2. Actualizar Rol
            RolesEntity rolesEntity = iRolesService.findById(usuariosDTO.getId_rol());
            if (rolesEntity != null) {
                usuariosExistentes.setRolesEntity(rolesEntity);
            }

            // 3. Actualizar Carrera
            CarrerasEntity carrerasEntity = iCarrerasService.findById(usuariosDTO.getId_carrera());
            if (carrerasEntity != null) {
                usuariosExistentes.setCarrerasEntity(carrerasEntity);
            }

            // 👇 4. ACTUALIZAR SEMILLERO (Lo que faltaba)
            if (usuariosDTO.getId_semillero() != null) {
                SemilleroEntity semillero = iSemilleroService.findById(usuariosDTO.getId_semillero());
                usuariosExistentes.setSemillero(semillero);
            } else {
                usuariosExistentes.setSemillero(null);
            }

            // 5. Guardar cambios
            UsuariosEntity actualizado = iUsuariosService.update(id, usuariosExistentes);

            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            System.out.println("Error al editar el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/semestresUsuario/{id}")
    public ResponseEntity<List<SemestresEntity>> obtenerSemestresPorUsuario(@PathVariable Long id) {
        UsuariosEntity usuario = iUsuariosService.findById(id);

        if (usuario == null) {
            System.out.println("Usuario no encontrado: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (usuario.getCarrerasEntity() == null) {
            System.out.println("El usuario no tiene carrera asignada: " + id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Long idCarrera = usuario.getCarrerasEntity().getIdCarrera();
        System.out.println("Carrera ID: " + idCarrera);

        List<SemestresEntity> semestres = iSemestresService.findByCarreraId(idCarrera);
        System.out.println("Semestres encontrados: " + semestres.size());

        return ResponseEntity.ok(semestres);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UsuarioSimpleDTO> buscarUsuario(@PathVariable("id") Long id) {
        UsuariosEntity usuario = iUsuariosService.findById(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UsuarioSimpleDTO dto = new UsuarioSimpleDTO();
        dto.setId_usuario(usuario.getIdUsuario());
        dto.setNombre_usuario(usuario.getNombreUsuario());
        dto.setApellido_usuario(usuario.getApellidoUsuario());
        dto.setCorreo_usuario(usuario.getCorreoUsuario());
        dto.setContrasena_usuario(usuario.getContrasenaUsuario());
        dto.setTelefono_usuario(usuario.getNumeroTelefono());
        dto.setDocumento_usuario(usuario.getNumeroDocumento());
        dto.setEstado(usuario.getEstado());

        dto.setId_rol(usuario.getRolesEntity().getIdRol());
        dto.setNombre_rol(usuario.getRolesEntity().getNombreRol());

        dto.setId_carrera(usuario.getCarrerasEntity().getIdCarrera());
        dto.setNombre_carrera(usuario.getCarrerasEntity().getNombreCarrera());

        if (usuario.getSemillero() != null) {
            dto.setId_semillero(usuario.getSemillero().getId());
        } else {
            dto.setId_semillero(null);
        }


        return ResponseEntity.ok(dto);
    }



    @GetMapping("/listarPorCarreraDelAdmin")
    public ResponseEntity<List<UsuarioSimpleDTO>> listarUsuariosPorCarreraDelAdmin() {
        return ResponseEntity.ok(iUsuariosService.listarUsuariosPorCarreraDelAdmin());
    }

    @GetMapping("/existe-correo")
    public ResponseEntity<Boolean> existeCorreo(@RequestParam String correo) {
        boolean existe = iUsuariosService.existeCorreo(correo);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/existe-documento/{documento}")
    public ResponseEntity<Boolean> verificarDocumento(@PathVariable String documento) {
        boolean existe = iUsuariosService.documentoExiste(documento);
        return ResponseEntity.ok(existe);
    }

    /*@PostMapping("/registrar-contrasena-temporal")
    public ResponseEntity<?> registrarConContrasenaTemporal(@RequestBody UsuariosEntity usuario) {
        boolean creado = iUsuariosService.registrarUsuarioConContrasenaTemporal(usuario);
        if (creado) {
            return ResponseEntity.ok("Usuario creado y contraseña temporal enviada al correo.");
        } else {
            return ResponseEntity.badRequest().body("El correo ya está registrado.");
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody UsuariosEntity usuario) {
        boolean registrado = iUsuariosService.registrarUsuarioConContrasenaTemporal(usuario);
        if (registrado) {
            return ResponseEntity.ok("Usuario registrado exitosamente. Revisa tu correo con la contraseña.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un usuario con ese correo.");
        }
    }*/







}