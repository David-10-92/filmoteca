package com.example.filmoteca.controller;

import com.example.filmoteca.dtos.RegisterUser;
import com.example.filmoteca.dtos.UserLogin;
import com.example.filmoteca.models.User;
import com.example.filmoteca.repository.UserRepository;
import com.example.filmoteca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";  // Nombre de la plantilla Thymeleaf sin la extensión .html
    }

    @PostMapping("/register")
    public String registerUser(RegisterUser registerUser, RedirectAttributes redirectAttributes) {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(registerUser.getEmail());
        userLogin.setPassword(registerUser.getPassword());
        userLogin.setRole(registerUser.getRole());
        String result = userService.addUser(userLogin);
        redirectAttributes.addFlashAttribute("message", result);
        return "login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/films";
    }
}
