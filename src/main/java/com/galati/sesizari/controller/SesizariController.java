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
            user.setRol(Rol.USER);
            userRepo.save(user);
            return "redirect:/login";

    }
    @GetMapping("/sesizare/noua")
    public String formular() { return "form-sesizare"; }
}

