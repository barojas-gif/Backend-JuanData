package com.jdc.repojuandata.service;

import com.jdc.repojuandata.DTO.DocumentoMasVistoDTO;
import com.jdc.repojuandata.config.EmailService;
import com.jdc.repojuandata.models.DocumentosEntity;
import com.jdc.repojuandata.models.UsuariosEntity;
import com.jdc.repojuandata.repository.DocumentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentosService {

    @Autowired
    private DocumentosRepository documentosRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @Transactional(readOnly = true)
    public List<DocumentosEntity> findAll() {
        return documentosRepository.findAll();
    }

    @Transactional(readOnly = true)
    public DocumentosEntity findById(Long id) {
        return documentosRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(DocumentosEntity documentosEntity) {
        documentosRepository.save(documentosEntity);
    }

    @Transactional
    public void deleteById(Long id) {
        documentosRepository.deleteById(id);
    }

    public List<DocumentosEntity> findByMateriaId(Long id) {
        return documentosRepository.findByMateriaId(id);
    }

    public List<DocumentosEntity> findByArchivoNombre(String archivoNombre) {
        return documentosRepository.findByArchivoDocumento(archivoNombre);
    }

    public List<DocumentoMasVistoDTO> obtenerDocumentosMasVistos() {
        return documentosRepository.obtenerDocumentosMasVistos();
    }

    public List<DocumentosEntity> findByEstado(int estado) {
        return documentosRepository.findByEstado(estado);
    }

    // Método que actualiza el estado y envía correo
    public void actualizarEstadoYEnviarCorreo(Long id, int estado, String mensaje, String correoEstudiante) {
        // Buscar documento
        documentosRepository.findById(id).ifPresent(documento -> {
            // Actualizar estado
            documento.setEstado(estado);
            documentosRepository.save(documento);

            // Datos adicionales
            String nombreArchivo = documento.getArchivoDocumento(); // ajusta al nombre real del campo
            String tema = documento.getTemaDocumento();                   // ajusta al nombre real del campo
            String nombreEstudiante = documento.getUsuariosEntity().getNombreUsuario(); // si está relacionado con usuario

            // Convertir estado numérico a texto
            String estadoTexto = switch (estado) {
                case 0 -> "Pendiente";
                case 1 -> "Aprobado";
                case 2 -> "Rechazado";
                default -> "Desconocido";
            };

            // Construir HTML del correo
            String htmlBody = """
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f6f9;">
    <table width="100%%" cellpadding="0" cellspacing="0" style="padding: 20px;">
        <tr>
            <td align="center">
                <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1);">
                    <tr>
                        <td style="color: #2c3e50; font-size: 24px; font-weight: bold; padding-bottom: 15px;">
                            Hola %s,
                        </td>
                    </tr>
                    <tr>
                        <td style="color: #333333; font-size: 16px; padding-bottom: 10px;">
                            Tu documento ha sido revisado por un moderador.
                        </td>
                    </tr>
                    <tr>
                        <td style="font-size: 15px; padding-bottom: 8px;"><strong>Nombre del archivo:</strong> %s</td>
                    </tr>
                    <tr>
                        <td style="font-size: 15px; padding-bottom: 8px;"><strong>Tema:</strong> %s</td>
                    </tr>
                    <tr>
                        <td style="font-size: 15px; padding-bottom: 8px;">
                            <strong>Estado:</strong> <span style="color: #007bff;">%s</span>
                        </td>
                    </tr>
                    <tr>
                        <td style="font-size: 15px; padding-bottom: 8px;"><strong>Mensaje del moderador:</strong></td>
                    </tr>
                    <tr>
                        <td style="font-size: 15px; padding-bottom: 20px;">%s</td>
                    </tr>
                    <tr>
                        <td style="font-size: 15px;">Te invitamos a ingresar al sistema para más detalles.</td>
                    </tr>
                    <tr>
                        <td style="font-size: 13px; color: #999999; text-align: right; padding-top: 30px;">
                            Gracias,<br>Equipo JuanData
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</body>
</html>
""".formatted(nombreEstudiante, nombreArchivo, tema, estadoTexto, mensaje);

            // Enviar correo
            emailService.enviarCorreo(correoEstudiante, "Actualización de estado de documento", htmlBody);
        });
    }

    public void actualizarEstado(Long idDocumento, int nuevoEstado) {
        documentosRepository.findById(idDocumento).ifPresent(documento -> {
            documento.setEstado(nuevoEstado);
            documentosRepository.save(documento);
        });
    }

    public List<DocumentosEntity> findBySemillero(Long idSemillero) {
        return documentosRepository.findBySemillero_Id(idSemillero);
    }

    public List<DocumentosEntity> findBySemilleroDelUsuarioAutenticado() {
        UsuariosEntity usuario = authenticatedUserService.getUsuarioAutenticado();

        if (usuario.getSemillero() == null) {
            return List.of(); // El usuario no tiene semillero asignado
        }

        Long idSemillero = usuario.getSemillero().getId();
        return documentosRepository.findBySemillero_Id(idSemillero);
    }
}
