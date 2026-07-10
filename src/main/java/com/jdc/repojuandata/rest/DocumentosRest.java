package com.jdc.repojuandata.rest;


import com.jdc.repojuandata.DTO.*;
import com.jdc.repojuandata.config.EmailService;
import com.jdc.repojuandata.models.*;
import com.jdc.repojuandata.repository.DocumentosRepository;
import com.jdc.repojuandata.repository.UsuarioRepository;
import com.jdc.repojuandata.repository.VistaRepository;
import com.jdc.repojuandata.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("api/documentos")
public class DocumentosRest {

    @Autowired
    private IDocumentosService iDocumentosService;

    @Autowired
    private IMateriasService iMateriasService;

    @Autowired
    private IUsuariosService iUsuariosService;

    @Autowired
    private IVistaService iVistaService;

    @Autowired
    private VistaRepository vistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DocumentosRepository documentosRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private DocumentosServiceImplment documentosServiceImplment;


    @GetMapping("/listar")
    public ResponseEntity<List<DocumentosEntity>> listarDocumentos() {
        return ResponseEntity.ok(iDocumentosService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<DocumentosEntity> listarDocumentosById(@PathVariable("id") Long id) {
        DocumentosEntity documentosEntity = iDocumentosService.findById(id);
        if (documentosEntity != null) {
            return ResponseEntity.ok(documentosEntity);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/deleteby/{id}")
    public ResponseEntity<String> deleteDocumentoById(@PathVariable("id") Long id) {
        try {
            DocumentosEntity aux = iDocumentosService.findById(id);
            if (aux == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el documento con el id: " + id);
            }
            iDocumentosService.deleteById(id);
            return ResponseEntity.ok("Documento eliminado con el id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar: " + e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<DocumentosEntity> editDocumento(@PathVariable("id") Long id, @Valid @RequestBody DocumentosDTO documentosDTO) {
        try {
            DocumentosEntity documentosExistentes = iDocumentosService.findById(id);
            if (documentosExistentes == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            documentosExistentes.setTemaDocumento(documentosDTO.getTema_documento());
            documentosExistentes.setTituloDocumento(documentosDTO.getTitulo_documento());
            documentosExistentes.setFechaDocumento(documentosDTO.getFecha_documento());
            documentosExistentes.setArchivoDocumento(documentosDTO.getArchivo_documento());
            documentosExistentes.setRutaArchivo(documentosDTO.getRuta_archivo());

            MateriasEntity materiasEntity = iMateriasService.findById(documentosDTO.getId_materia());
            if (materiasEntity == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            documentosExistentes.setMateriasEntity(materiasEntity);

            UsuariosEntity usuariosEntity = iUsuariosService.findById(documentosDTO.getId_usuario());
            if (usuariosEntity == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            documentosExistentes.setUsuariosEntity(usuariosEntity);

            iDocumentosService.save(documentosExistentes);
            return ResponseEntity.ok(documentosExistentes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/porMateria/{id}")
    public ResponseEntity<List<DocumentosDTO>> listarDocumentosPorMateria(@PathVariable("id") Long id) {
        try {
            List<DocumentosEntity> documentos = iDocumentosService.findByMateriaId(id);

            List<DocumentosDTO> dtos = documentos.stream().map(doc -> {
                DocumentosDTO dto = new DocumentosDTO();
                dto.setId_documento(doc.getIdDocumento());
                dto.setTitulo_documento(doc.getTituloDocumento());
                dto.setTema_documento(doc.getTemaDocumento());
                dto.setArchivo_documento(doc.getArchivoDocumento());
                dto.setRuta_archivo(doc.getRutaArchivo());
                dto.setFecha_documento(doc.getFechaDocumento());
                dto.setId_materia(doc.getMateriasEntity().getId_materia());
                dto.setId_usuario(doc.getUsuariosEntity().getIdUsuario());
                dto.setEstado(doc.getEstado());
                return dto;
            }).toList();

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename,
                                              @RequestParam(value = "id_usuario", required = false) Long idUsuario) {
        try {
            filename = java.net.URLDecoder.decode(filename, "UTF-8");
            List<DocumentosEntity> documentos = iDocumentosService.findByArchivoNombre(filename);
            if (documentos == null || documentos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            DocumentosEntity documento = documentos.get(0);
            Path filePath = Paths.get(documento.getRutaArchivo());
            System.out.println("Ruta archivo: " + filePath.toString());

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Registrar vista solo si id_usuario viene
            if (idUsuario != null) {
                UsuariosEntity usuario = iUsuariosService.findById(idUsuario);
                if (usuario != null) {
                    VistaEntity vista = new VistaEntity();
                    vista.setDocumento(documento);
                    vista.setUsuario(usuario);
                    vista.setFechaVista(LocalDateTime.now());
                    iVistaService.save(vista);
                }
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("titulo") String titulo,
            @RequestParam("tema") String tema,
            @RequestParam("id_usuario") Long idUsuario,
            @RequestParam("id_materia") Long idMateria
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (file.isEmpty()) {
                response.put("message", "Archivo vacío");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();
            Files.createDirectories(uploadDir);

            Path filePath = uploadDir.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            DocumentosEntity documento = new DocumentosEntity();
            documento.setTituloDocumento(titulo);
            documento.setTemaDocumento(tema);
            documento.setFechaDocumento(new Date());

            // 📌 Guardar nombre real del archivo
            documento.setArchivoDocumento(file.getOriginalFilename());

            // Si tu entidad tiene un campo nombreDocumento:
            // documento.setNombreDocumento(file.getOriginalFilename());

            documento.setRutaArchivo(filePath.toString());
            documento.setEstado(0);

            UsuariosEntity usuario = iUsuariosService.findById(idUsuario);
            if (usuario == null) {
                response.put("message", "Usuario no encontrado con ID: " + idUsuario);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            documento.setUsuariosEntity(usuario);

            MateriasEntity materia = iMateriasService.findById(idMateria);
            if (materia == null) {
                response.put("message", "Materia no encontrada con ID: " + idMateria);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            documento.setMateriasEntity(materia);

            iDocumentosService.save(documento);

            // Enviar correo

            String asunto = "Nuevo documento subido";

            String cuerpo = """
    <html>
    <head>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f4f6f9;
                padding: 20px;
                margin: 0;
            }
            .card {
                max-width: 600px;
                margin: auto;
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                overflow: hidden;
                border: 1px solid #e0e0e0;
            }
            .card-header {
                background: linear-gradient(135deg, #007bff, #0056b3);
                color: white;
                padding: 15px 20px;
                font-size: 18px;
                font-weight: bold;
            }
            .card-body {
                padding: 20px;
            }
            .info {
                margin: 10px 0;
                font-size: 15px;
                color: #333;
            }
            .info strong {
                color: #007bff;
            }
            .card-footer {
                background-color: #f8f9fa;
                padding: 15px;
                text-align: center;
                font-size: 12px;
                color: #6c757d;
                border-top: 1px solid #e0e0e0;
            }
            .btn {
                display: inline-block;
                margin-top: 15px;
                padding: 10px 18px;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 8px;
                font-size: 14px;
                font-weight: bold;
                transition: background-color 0.3s;
            }
            .btn:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="card">
            <div class="card-header">📢 Nuevo documento subido</div>
            <div class="card-body">
                <p class="info"><strong>Título:</strong> %s</p>
                <p class="info"><strong>Tema:</strong> %s</p>
                <p class="info"><strong>Materia:</strong> %s</p>
                <p class="info"><strong>Usuario:</strong> %s</p>
                <p class="info"><strong>Archivo:</strong> %s</p>
                
            </div>
            <div class="card-footer">
                Este es un mensaje automático, por favor no responder.
            </div>
        </div>
    </body>
    </html>
    """.formatted(
                    titulo,
                    tema,
                    materia.getNombre_materia(),
                    usuario.getNombreUsuario(),
                    file.getOriginalFilename()
            );

            emailService.enviarCorreo("brianandres54321@gmail.com", asunto, cuerpo);



            String fileUrl = "/api/documentos/uploads/" + file.getOriginalFilename();
            response.put("fileUrl", fileUrl);
            response.put("message", "Archivo subido correctamente");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Error al subir el archivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @GetMapping("/documentos/mas-vistos")
    public List<DocumentoMasVistoDTO> obtenerMasVistos() {
        return iDocumentosService.obtenerDocumentosMasVistos();
    }


    @PostMapping("/registrar-vista")
    public ResponseEntity<?> registrarVista(@RequestBody VistaEntity vista) {
        vista.setFechaVista(LocalDateTime.now());
        vistaRepository.save(vista);
        return ResponseEntity.ok("Vista registrada");
    }


    //Metodo para aceptar los documentos
    @PutMapping("/aceptar/{id}")
    public ResponseEntity<?> aceptarDocumento(@PathVariable Long id) {
        DocumentosEntity documento = iDocumentosService.findById(id);
        if (documento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento no encontrado con ID: " + id);
        }

        documento.setEstado(1); // Cambiar el estado a aceptado
        iDocumentosService.save(documento);

        return ResponseEntity.ok("Documento aceptado exitosamente");
    }


    @GetMapping("/pendientes")
    public ResponseEntity<List<DocumentoPendienteDTO>> obtenerDocumentosPendientes(Authentication authentication) {

        // Obtener usuario autenticado por su correo (que está en el token JWT)
        UsuariosEntity admin = usuarioRepository.findByCorreoUsuario(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Long idCarrera = admin.getCarrerasEntity().getIdCarrera();

        // Buscar solo documentos con estado 0 (pendiente) de esa carrera
        List<DocumentosEntity> documentos = documentosRepository.findByEstadoAndCarrera(0, idCarrera);

        List<DocumentoPendienteDTO> dtos = documentos.stream()
                .map(doc -> new DocumentoPendienteDTO(
                        doc.getIdDocumento(),
                        doc.getUsuariosEntity().getNombreUsuario(), // nombre del estudiante
                        doc.getUsuariosEntity().getApellidoUsuario(),
                        doc.getFechaDocumento(),
                        doc.getTituloDocumento(),
                        doc.getTemaDocumento(),
                        doc.getArchivoDocumento(),
                        doc.getUsuariosEntity().getCorreoUsuario(),
                        doc.getMateriasEntity().getSemestresEntity().getNumero_semestre() // semestre
                ))
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/pendientes/count")
    public ResponseEntity<Long> obtenerCantidadPendientes(Authentication authentication) {
        UsuariosEntity admin = usuarioRepository.findByCorreoUsuario(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Long idCarrera = admin.getCarrerasEntity().getIdCarrera();

        Long cantidad = documentosRepository.countByEstadoAndCarrera(0, idCarrera);
        return ResponseEntity.ok(cantidad);
    }



    @GetMapping("/aceptados")
    public ResponseEntity<List<DocumentoPendienteDTO>> obtenerDocumentosAceptados(Authentication authentication) {
        String correo = authentication.getName(); // correo del administrador autenticado
        UsuariosEntity admin = usuarioRepository.findByCorreoUsuario(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Long idCarreraAdmin = admin.getCarrerasEntity().getIdCarrera();

        List<DocumentosEntity> documentos = iDocumentosService.findByEstado(1);

        // Filtrar solo los documentos que pertenecen a materias de la misma carrera del admin
        List<DocumentoPendienteDTO> dtos = documentos.stream()
                .filter(doc -> doc.getMateriasEntity().getCarrerasEntity().getIdCarrera().equals(idCarreraAdmin))
                .map(doc -> new DocumentoPendienteDTO(
                        doc.getIdDocumento(),
                        doc.getUsuariosEntity().getNombreUsuario(),
                        doc.getUsuariosEntity().getApellidoUsuario(),
                        doc.getFechaDocumento(),
                        doc.getTituloDocumento(),
                        doc.getTemaDocumento(),
                        doc.getArchivoDocumento(),
                        doc.getUsuariosEntity().getCorreoUsuario(),
                        doc.getMateriasEntity().getSemestresEntity().getNumero_semestre()
                ))
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/documentos/{id}/estado")
    public ResponseEntity<String> actualizarEstadoYNotificar(
            @PathVariable Long id,
            @RequestParam int estado,
            @RequestParam String mensaje,
            @RequestParam String correoEstudiante) {

        System.out.println("📥 PUT recibido con id=" + id + ", estado=" + estado + ", correo=" + correoEstudiante);

        try {
            iDocumentosService.actualizarEstadoYEnviarCorreo(id, estado, mensaje, correoEstudiante);
            return ResponseEntity.ok("Documento actualizado y correo enviado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/por-semillero")
    public ResponseEntity<List<DocumentosEntity>> obtenerPorSemilleroDelUsuario() {
        List<DocumentosEntity> documentos = iDocumentosService.findBySemilleroDelUsuarioAutenticado();
        return ResponseEntity.ok(documentos);
    }

    @PostMapping("/upload-semillero")
    public ResponseEntity<Map<String, Object>> uploadFileSemillero(
            @RequestParam("file") List<MultipartFile> files,
            @RequestParam("titulo") String titulo,
            @RequestParam("tema") String tema,
            @RequestParam("id_usuario") Long idUsuario,
            @RequestParam("id_semillero") Long idSemillero,
            @RequestParam("categoria") String categoria
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (files == null || files.isEmpty()) {
                response.put("message", "No se recibieron archivos");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();
                Files.createDirectories(uploadDir);

                Path filePath = uploadDir.resolve(file.getOriginalFilename());
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                DocumentosEntity documento = new DocumentosEntity();
                documento.setTituloDocumento(titulo);
                documento.setTemaDocumento(tema);
                documento.setFechaDocumento(new Date());
                documento.setArchivoDocumento(file.getOriginalFilename());
                documento.setRutaArchivo(filePath.toString());
                documento.setEstado(1);
                documento.setCategoria(categoria);

                UsuariosEntity usuario = iUsuariosService.findById(idUsuario);
                if (usuario == null) continue;
                documento.setUsuariosEntity(usuario);

                SemilleroEntity semillero = semilleroService.findById(idSemillero);
                if (semillero == null) continue;
                documento.setSemillero(semillero);

                iDocumentosService.save(documento);
            }

            response.put("message", "Archivos de semillero subidos correctamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Error al subir los archivos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Autowired
    private ISemilleroService semilleroService;

    @GetMapping("/semillero/{id}")
    public List<DocumentosEntity> getDocumentosPorSemillero(@PathVariable Long id) {
        List<DocumentosEntity> docs = documentosServiceImplment.findBySemilleroId(id);
        List<DocumentosEntity> activos = docs.stream()
                .filter(doc -> doc.getEstado() == 1)
                .toList();

        activos.forEach(doc -> {
            doc.setUsuariosEntity(null);
            doc.setMateriasEntity(null);
            doc.setSemillero(null);
        });

        return activos;
    }


    @PutMapping("/semillero/{id}/estado")
    public ResponseEntity<String> desactivarDocumentoSemillero(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, Object> body,
            @RequestParam(value = "estado", required = false) Integer estadoParam) {
        try {
            DocumentosEntity doc = iDocumentosService.findById(id);
            if (doc == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento no encontrado");
            }

            Integer nuevoEstado = estadoParam;
            if (nuevoEstado == null && body != null) {
                Object v = body.get("estado");
                if (v instanceof Number) nuevoEstado = ((Number) v).intValue();
            }
            if (nuevoEstado == null) {
                return ResponseEntity.badRequest().body("Falta parámetro 'estado'.");
            }

            doc.setEstado(nuevoEstado);
            iDocumentosService.save(doc);
            return ResponseEntity.ok("Estado actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar estado: " + e.getMessage());
        }
    }


    @GetMapping("/usuario/semillero")
    public ResponseEntity<List<DocumentosEntity>> obtenerDocumentosPorSemilleroUsuario() {
        // Obtener el correo del usuario autenticado
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar el usuario
        UsuariosEntity usuario = usuarioRepository.findByCorreoUsuario(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar que tenga semillero
        if (usuario.getSemillero() == null) {
            return ResponseEntity.noContent().build();
        }

        // Traer documentos del semillero asociado al usuario
        List<DocumentosEntity> documentos = documentosRepository.findBySemillero(usuario.getSemillero());

        if (documentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(documentos);
    }



    @GetMapping("/usuario/{idUsuario}/documentos")
    public ResponseEntity<List<DocumentoSemilleroDTO>> obtenerDocumentosPorUsuario(
            @PathVariable Long idUsuario) {
        try {
            List<DocumentoSemilleroDTO> documentos = documentosRepository.obtenerDocumentosPorUsuario(idUsuario);
            if (documentos == null || documentos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


    @GetMapping("/usuario/mi-semillero")
    public ResponseEntity<?> obtenerSemilleroDelUsuario() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();

        UsuariosEntity usuario = usuarioRepository.findByCorreoUsuario(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getSemillero() == null) {
            return ResponseEntity.noContent().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("idSemillero", usuario.getSemillero().getId());
        response.put("nombreSemillero", usuario.getSemillero().getNombre());

        return ResponseEntity.ok(response);
    }

}
