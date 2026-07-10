package com.jdc.repojuandata.config;

import com.jdc.repojuandata.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UsuariosDetailsService usuariosDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/recuperar",
                                "/api/auth/register"
                        ).permitAll()
                        .requestMatchers("/api/auth/actualizar-contrasena").authenticated()
                        .requestMatchers("/api/documentos/uploads/**").permitAll()
                        .requestMatchers("/api/documentos/upload").hasAnyRole("Administrador", "Estudiante", "Docente")
                        .requestMatchers("/api/documentos/upload-semillero").hasAnyRole("Administrador", "Docente")
                        .requestMatchers("/api/documentos/registrar-vista").hasAnyRole("Administrador", "Estudiante")
                        .requestMatchers("/api/documentos/porMateria/**").hasAnyRole("Administrador", "Estudiante", "Docente")
                        .requestMatchers("/api/documentos/semillero/*/estado").hasAnyRole("Administrador", "Estudiante", "Docente")
                        .requestMatchers("/api/documentos/semillero/**").hasAnyRole("Administrador", "Estudiante", "Docente")
                        .requestMatchers("/api/documentos/usuario/mi-semillero").hasAnyRole("Administrador", "Estudiante", "Docente")
                        .requestMatchers("/api/documentos/*/estado").hasRole("Administrador")
                        .requestMatchers("/api/documentos/aceptar/**").hasRole("Administrador")
                        .requestMatchers("/api/admin/**").hasRole("Administrador")
                        .requestMatchers("/api/estudiante/**").hasRole("Estudiante")
                        .requestMatchers("/api/roles/**").authenticated()
                        .requestMatchers("/api/carreras/listar").permitAll()
                        .requestMatchers("/api/carreras/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/semilleros", "/api/semilleros/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/semilleros/**").hasRole("Administrador")
                        .requestMatchers(HttpMethod.PUT, "/api/semilleros/**").hasRole("Administrador")
                        .requestMatchers("/api/semilleros/**").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(parseAllowedOrigins()));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true); // Importante para JWT con cookies o headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuariosDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("✅ Bean PasswordEncoder creado");
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    private String[] parseAllowedOrigins() {
        return Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(origin -> !origin.isEmpty())
                .toArray(String[]::new);
    }
}
