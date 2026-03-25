package com.galati.sesizari.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Controller
@RequestMapping("/v0.1/home") // Adaugă un slash la început pentru siguranță
public class SesizariController {

    @GetMapping("/buna") // Adaugă un slash aici pentru a forma ruta completă
    public String returneaza(Model model) {
        model.addAttribute("data", LocalDate.now());

        // Caută fișierul src/main/resources/templates/index.html
        return "index";
    }
}