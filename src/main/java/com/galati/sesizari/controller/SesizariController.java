package com.galati.sesizari.controller;

import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Rol;
import com.galati.sesizari.repos.UserRepo;
import com.galati.sesizari.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class SesizariController {

    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String home() { return "index"; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @PostMapping("/login")
    public String proceseazaLogin(@ModelAttribute User user, HttpSession session, Model model) {
        User userLogat = userService.loginUser(user);

        if (userLogat != null) {
            if (userLogat.getRol() == Rol.USER) {
                session.setAttribute("utilizatorLogat", userLogat);
                return "redirect:/sesizare/noua";
            } else {
                model.addAttribute("error", "Accesul este permis doar cetățenilor. Creati-va un cont mai jos");
                return "login";
            }

        } else {
            if (!userService.verificaUsername(user.getUsername())) {
                model.addAttribute("error", "Username-ul nu există!");
            } else {
                model.addAttribute("error", "Parolă incorectă!");
            }
            return "login";
        }}
    @GetMapping("/register")
    public String register() { return "register"; }

    @PostMapping("/register")
    public String proceseazaRegister(@ModelAttribute User user) {
            user.setRol(Rol.USER);
            userService.registerUser(user);
            return "redirect:/login";
    }
    @GetMapping("/sesizare/noua")
    public String formular() { return "form-sesizare"; }
}

