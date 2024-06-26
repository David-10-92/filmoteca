package com.example.filmoteca.security;

import com.example.filmoteca.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/films/addFilm", "/films/updateFilm/**", "/films/deleteFilm").hasAuthority("ROLE_ADMIN") // Requiere rol ADMIN para añadir, actualizar o eliminar película
                        .requestMatchers("/**").permitAll() // Permitir acceso a todas las demás rutas sin autenticación
                        .anyRequest().authenticated()) // Cualquier otra solicitud requiere autenticación
                .formLogin(form -> form
                        .loginPage("/users/login").permitAll()
                        .defaultSuccessUrl("/films", true)) // Permitir acceso a la página de login sin autenticación
                .logout(logout -> logout
                        .logoutSuccessUrl("/films?logout").permitAll()); // Redirigir a la página de login después de cerrar sesión
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
