package com.example.filmoteca.service;

import com.example.filmoteca.dtos.UserLogin;
import com.example.filmoteca.models.User;

public interface UserService {
    String addUser(UserLogin userLogin);
    String updateUser(int id, UserLogin userLogin);
    String deleteUser(int id);
}
