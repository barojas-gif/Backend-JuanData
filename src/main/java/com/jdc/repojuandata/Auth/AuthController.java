package com.jdc.repojuandata.Auth;

import com.jdc.repojuandata.DTO.RecuperarRequest;
import com.jdc.repojuandata.config.EmailService;
import com.jdc.repojuandata.jwt.JwtService;
import com.jdc.repojuandata.models.UsuariosEntity;
import com.jdc.repojuandata.models.RolesEntity;
import com.jdc.repojuandata.models.CarrerasEntity;
import com.jdc.repojuandata.repository.UsuarioRepository;
import com.jdc.repojuandata.repository.RolesRepository;
import com.jdc.repojuandata.repository.CarrerasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuariosRepository;
    private final RolesRepository rolesRepository;
    private final CarrerasRepository carrerasRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        System.out.println("Correo recibido: " + request.getCorreoUsuario());
        System.out.println("Contraseña recibida: " + request.getContrasenaUsuario());

        UsuariosEntity usuario = usuariosRepository
                .findByCorreoUsuario(request.getCorreoUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        System.out.println("Contraseña en DB: " + usuario.getContrasenaUsuario());
        boolean match = passwordEncoder.matches(request.getContrasenaUsuario(), usuario.getContrasenaUsuario());
        System.out.println("¿Coincide la contraseña?: " + match);

        if (!match) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        String token = jwtService.generateToken(usuario);
        System.out.println("Token generado: " + token);

        // Ahora devolvemos token + idUsuario + rol
        return ResponseEntity.ok(new AuthResponse(
                token,
                usuario.getIdUsuario(),
                usuario.getRolesEntity().getNombreRol(), //se llama al rol
                usuario.getCarrerasEntity().getIdCarrera(), //se llama al idcarrera
                usuario.getNombreUsuario(),// SE LLAMA EL NOMBRE PARA EL SALUDO
                usuario.isTemporalContrasena() //AQUÍ ENVIAS SI LA CONTRASEÑA ES TEMPORAL
        ));
    }


    @PostMapping("/recuperar")
    public ResponseEntity<?> recuperarContrasena(@RequestBody RecuperarRequest request) {
        String username = request.getUsername();

        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo 'username' es obligatorio.");
        }

        Optional<UsuariosEntity> optionalUsuario = usuariosRepository.findByCorreoUsuario(username.trim());

        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no está registrado.");
        }

        UsuariosEntity usuario = optionalUsuario.get();

        // Generar contraseña temporal
        String contrasenaTemporal = generarContrasenaTemporal();

        // Encriptar y guardar
        usuario.setContrasenaUsuario(passwordEncoder.encode(contrasenaTemporal));
        usuario.setTemporalContrasena(true); // Marca como temporal
        usuariosRepository.save(usuario);

        // Enviar correo con HTML
        String asunto = "Recuperación de contraseña - JuanData";

        String mensajeHtml = """
        <html>
          <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
            <div style="max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 30px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
              <h2 style="color: #2c3e50;">Hola %s,</h2>
              <p style="font-size: 16px; color: #333;">Hemos recibido una solicitud para restablecer tu contraseña.</p>
              <p style="font-size: 16px; color: #333;">Tu nueva contraseña temporal es:</p>
              <div style="background-color: #f0f0f0; padding: 12px 20px; border-radius: 6px; font-size: 18px; font-weight: bold; color: #2c3e50; text-align: center;">
                %s
              </div>
              <p style="font-size: 16px; color: #333; margin-top: 20px;">
                Por seguridad, te recomendamos iniciar sesión y cambiarla inmediatamente.
              </p>
              <hr style="margin-top: 30px;" />
              <p style="font-size: 14px; color: #888;">Si no solicitaste este cambio, puedes ignorar este mensaje.</p>
              <p style="font-size: 14px; color: #888;">— Equipo JuanData</p>
            </div>
          </body>
        </html>
        """.formatted(usuario.getNombreUsuario(), contrasenaTemporal);

        emailService.enviarCorreo(usuario.getCorreoUsuario(), asunto, mensajeHtml);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Se ha enviado una nueva contraseña temporal al correo.");
        return ResponseEntity.ok(response);
    }




    //GENERAR CONTRASEÑA TEMPORAL
    private String generarContrasenaTemporal() {
        int longitud = 10;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < longitud; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        return sb.toString();
    }



    // REGISTRO
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Validar correo existente
        if (usuariosRepository.findByCorreoUsuario(request.getCorreoUsuario()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }

        // Validar documento duplicado
        if (usuariosRepository.findByNumeroDocumento(request.getNumeroDocumento()).isPresent()) {
            return ResponseEntity.badRequest().body("El número de documento ya está registrado");
        }

        // Obtener rol fijo estudiante
        RolesEntity rol = rolesRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Rol de estudiante no encontrado"));

        // Validar carrera
        CarrerasEntity carrera = carrerasRepository.findById(request.getIdCarrera())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        // Generar contraseña temporal aleatoria
        String contrasenaTemporal = generarContrasenaTemporal();

        // Crear y guardar usuario
        UsuariosEntity nuevoUsuario = new UsuariosEntity();
        nuevoUsuario.setNumeroDocumento(request.getNumeroDocumento());
        nuevoUsuario.setNombreUsuario(request.getNombreUsuario());
        nuevoUsuario.setApellidoUsuario(request.getApellidoUsuario());
        nuevoUsuario.setNumeroTelefono(request.getNumeroTelefono());
        nuevoUsuario.setCorreoUsuario(request.getCorreoUsuario());
        nuevoUsuario.setContrasenaUsuario(passwordEncoder.encode(contrasenaTemporal));
        nuevoUsuario.setRolesEntity(rol);
        nuevoUsuario.setCarrerasEntity(carrera);
        nuevoUsuario.setTemporalContrasena(true);
        nuevoUsuario.setEstado(1);

        usuariosRepository.save(nuevoUsuario);

        // Enviar correo con contraseña temporal
        String asunto = "¡Bienvenido a JuanData!";
        String mensajeHtml = """
    <html>
      <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
        <div style="max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 30px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
          <h2 style="color: #2c3e50;">¡Bienvenido a JuanData, %s %s!</h2>
          <p style="font-size: 16px; color: #333;">Gracias por registrarte en nuestra plataforma educativa.</p>
          <p style="font-size: 16px; color: #333;">Tu contraseña temporal es:</p>
          <div style="background-color: #f0f0f0; padding: 12px 20px; border-radius: 6px; font-size: 18px; font-weight: bold; color: #2c3e50; text-align: center;">
            %s
          </div>
          <p style="font-size: 16px; color: #333; margin-top: 20px;">
            Por favor inicia sesión y cambia tu contraseña lo antes posible para mantener tu cuenta segura.
          </p>
          <a href="http://localhost:4200/login" style="display: inline-block; margin-top: 20px; padding: 12px 24px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 4px;">Ir al Login</a>
          <hr style="margin-top: 30px;" />
          <p style="font-size: 14px; color: #888;">— Equipo JuanData</p>
        </div>
      </body>
    </html>
    """.formatted(
                nuevoUsuario.getNombreUsuario(),
                nuevoUsuario.getApellidoUsuario(),
                contrasenaTemporal
        );
        emailService.enviarCorreo(nuevoUsuario.getCorreoUsuario(), asunto, mensajeHtml);

        // Generar token
        String token = jwtService.generateToken(nuevoUsuario);

        return ResponseEntity.ok(new AuthResponse(
                token,
                nuevoUsuario.getIdUsuario(),
                nuevoUsuario.getRolesEntity().getNombreRol(),
                nuevoUsuario.getCarrerasEntity().getIdCarrera(),
                nuevoUsuario.getNombreUsuario(),
                true
        ));
    }





    @PutMapping("/actualizar-contrasena")
    @Transactional
    public ResponseEntity<?> actualizarContrasena(@RequestBody Map<String, String> body, Principal principal) {
        String nueva = body.get("nuevaContrasena");

        UsuariosEntity usuario = usuariosRepository.findByCorreoUsuario(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        usuario.setContrasenaUsuario(passwordEncoder.encode(nueva));
        usuario.setTemporalContrasena(false);
        usuariosRepository.save(usuario);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Contraseña actualizada correctamente.");
        return ResponseEntity.ok(response);
    }
}
