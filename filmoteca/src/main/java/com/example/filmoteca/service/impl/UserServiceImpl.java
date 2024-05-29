package com.example.filmoteca.service.impl;

import com.example.filmoteca.dtos.UserLogin;
import com.example.filmoteca.models.User;
import com.example.filmoteca.repository.UserRepository;
import com.example.filmoteca.service.UserService;
import com.example.filmoteca.service.errors.ErrorCode;
import com.example.filmoteca.service.errors.ServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addUser(UserLogin userLogin) {
        if (userLogin.getEmail() == null || userLogin.getEmail().isEmpty() ||
                userLogin.getPassword() == null || userLogin.getPassword().isEmpty()) {
            return "Los parámetros no pueden estar vacíos o ser nulos";
        }

        if (userRepository.findByEmail(userLogin.getEmail()).isPresent()) {
            return "El usuario ya existe";
        }

        User user = new User();
        user.setEmail(userLogin.getEmail());
        user.setPassword(passwordEncoder.encode(userLogin.getPassword())); // Codificar la contraseña
        user.setRole("ADMIN"); // Asignar el rol

        userRepository.save(user);
        return "Usuario creado correctamente";

    }

    @Override
    public String updateUser(int id, UserLogin userLogin) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User existUser = optionalUser.get();
            if(userLogin.getPassword() != null && !userLogin.getPassword().isEmpty()){
                existUser.setPassword(userLogin.getPassword());
            }
            if(userLogin.getEmail() != null && !userLogin.getEmail().isEmpty()){
                existUser.setEmail(userLogin.getEmail());
            }
            userRepository.save(existUser);
            return "Usuario modificada correctamente";
        }else {
            throw new ServiceError(ErrorCode.RESOURCE_NOT_FOUND, "No se encontró el usuario con el ID proporcionado");
        }
    }

    @Override
    public String deleteUser(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User existUser = optionalUser.get();
            userRepository.delete(existUser);
            return "Usuario eleminiado correctamente";
        }else {
            throw new ServiceError(ErrorCode.RESOURCE_NOT_FOUND, "No se encontró el usuario con el ID proporcionado");
        }
    }

}
