package com.makethedifference.mtd.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configuración CORS
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("*"));
            configuration.setAllowedOrigins(List.of("http://localhost:4200"));
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
            configuration.setAllowCredentials(true);
            return configuration;
        }));
        //Cross-site request forgery (CSRF)
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers(HttpMethod.GET).permitAll() // Permite todas las solicitudes GET
                                .requestMatchers(HttpMethod.POST).permitAll() // Permite todas las solicitudes POST
                                .requestMatchers(HttpMethod.PUT).permitAll() // Permite todas las solicitudes PUT
                                .requestMatchers(HttpMethod.DELETE).permitAll() // Permite todas las solicitudes DELETE
                                .requestMatchers(HttpMethod.OPTIONS).permitAll() // Permite todas las solicitudes OPTIONS
                                .requestMatchers("/usuario/**").permitAll() // Permite todas las solicitudes a /usuario/**
                                .requestMatchers("/healthcheck").permitAll() // Permite todas las solicitudes a /healthcheck
                                .requestMatchers("/swagger-ui.html", "/v3/api-docs/*","/swagger-ui/*").permitAll() // Permite acceso a la documentación Swagger
                                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura sesiones sin estado (stateless)
                .authenticationProvider(authProvider) // Configura el proveedor de autenticación
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Agrega el filtro JWT antes del filtro de autenticación de nombre de usuario y contraseña
                .build();
    }}
