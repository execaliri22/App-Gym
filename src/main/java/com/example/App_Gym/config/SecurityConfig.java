package com.example.App_Gym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    // Inyectamos el filtro de JWT
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Origen de tu App Angular
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true); // Permitir el envío de cookies o auth headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Deshabilitar CSRF (necesario para APIs REST)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                
                // 2. Deshabilitar Basic Auth y Form Login para que no pida contraseña por defecto
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())

                // 3. Política de sesión sin estado (Stateless) porque usamos JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // 4. Reglas de autorización
                .authorizeHttpRequests(authz -> authz
                // Permitir registro y login sin token
                .requestMatchers("/api/auth/**").permitAll() 

                // --- REGLAS PARA AVISOS ---
                // Cualquier usuario logueado puede ver avisos, pero solo ADMIN puede crear/editar/borrar
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/avisos/**").authenticated()
                .requestMatchers("/api/avisos/**").hasRole("ADMIN")

                // --- REGLAS PARA PLANES DE ENTRENAMIENTO ---
                // El socio solo puede ver SU plan
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/planes/mi-plan").authenticated()
                // El Admin gestiona todos los planes y asignaciones
                .requestMatchers("/api/planes/**").hasRole("ADMIN")
                
                // Solo administradores pueden entrar a las rutas /api/admin/
                .requestMatchers("/api/admin/**").hasRole("ADMIN") 
                // --- REGLAS PARA PLANES DE ALIMENTACIÓN
                // El socio solo puede ver SU dieta
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/alimentacion/mi-dieta").authenticated()
                // El Admin gestiona todas las dietas y asignaciones
                .requestMatchers("/api/alimentacion/**").hasRole("ADMIN")
                // --- REGLAS PARA PERFIL ---
                // Permite que socios y admins vean y editen su propia información
                .requestMatchers("/api/perfil/**").authenticated()

                // --- REGLAS DE ADMINISTRACIÓN ---
                // Solo administradores pueden gestionar usuarios, promover roles y ver estadísticas
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                
                // --- NUEVAS REGLAS DE COBROS (ADMIN) ---
                // Solo el administrador puede registrar, editar o eliminar cobros
                .requestMatchers("/api/cobros/**").hasRole("ADMIN")

                // --- REGLAS DE PAGOS (SOCIO) ---
                // El socio consulta sus pagos, el admin puede ver todos si fuera necesario
                .requestMatchers("/api/pagos/mis-pagos").authenticated()

               // --- REGLAS PARA EL CHAT ---
                // Permite que el socio envíe preguntas y vea su propio historial
                .requestMatchers("/api/chat/preguntar", "/api/chat/mis-mensajes").authenticated()

                // Solo el administrador puede responder o ver chats de otros socios específicos
                .requestMatchers("/api/chat/admin/**").hasRole("ADMIN")

                // Cualquier otra ruta requiere que el usuario esté autenticado
                .anyRequest().authenticated()
            )
            
            // 5. Agregar el filtro de JWT antes del filtro de autenticación de Spring
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}