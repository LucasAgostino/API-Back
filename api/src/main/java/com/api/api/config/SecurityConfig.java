package com.api.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilita CSRF solo para pruebas
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/auth/login").permitAll() // Permite acceso al login
                    .requestMatchers("/register").permitAll() // Permite acceso al registro
                    .requestMatchers("/**").permitAll()
                    .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
            )
            .formLogin(formLogin ->
                formLogin
                    .loginProcessingUrl("/api/auth/login") // No debería redirigir
                    .disable() // Deshabilita el formulario de inicio de sesión predeterminado
            )
            .logout(logout ->
                logout.permitAll() // Permite el acceso a la funcionalidad de cierre de sesión
            );

        return http.build();
    }
}
