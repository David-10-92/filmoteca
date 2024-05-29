package com.example.filmoteca.service.impl;

import com.example.filmoteca.models.User;
import com.example.filmoteca.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado " + email));

        // Verificar si la contraseña ingresada coincide con la almacenada en la base de datos
        String storedPassword = user.getPassword();
        boolean passwordMatches = passwordEncoder.matches(user.getPassword(), storedPassword);

        if (!passwordMatches) {
            throw new BadCredentialsException("Credenciales inválidas"); // Otra excepción puede ser lanzada aquí, dependiendo de tu lógica de manejo de errores
        }

        // Si la contraseña es correcta, crear UserDetails y devolverlo
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
    }
}
