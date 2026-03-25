package com.galati.sesizari.controller;

import com.galati.sesizari.clase.Sesizari;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class SesizariController {

    @GetMapping("/")
    public String home() {
        return "index"; // Caută templates/index.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Caută templates/login.html
    }

    @PostMapping("/login")
    public String proceseazaLogin(@RequestParam String username, @RequestParam String password) {
        return "redirect:/sesizare/noua";
    }

    @GetMapping("/sesizare/noua")
    public String formular() {
        return "form-sesizare";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }

}