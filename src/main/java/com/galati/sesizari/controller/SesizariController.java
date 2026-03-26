package com.galati.sesizari.controller;

import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Rol;
import com.galati.sesizari.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class SesizariController {

    // 1. Injectăm Repository-ul (sau Service-ul dacă ai unul)
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String home() { return "index"; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/register")
    public String register() { return "register"; }

    @PostMapping("/register")
    public String proceseazaRegister(@ModelAttribute User user) {
        // 2. Setăm rolul implicit (pentru a evita erori de tip null în DB)
        user.setRol(Rol.USER);

        // 3. Salvăm obiectul în baza de date
        userRepo.save(user);

        // 4. Redirecționăm către login după succes
        return "redirect:/login";
    }

    @GetMapping("/sesizare/noua")
    public String formular() { return "form-sesizare"; }
}

