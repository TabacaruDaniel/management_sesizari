package com.galati.sesizari.controller;

import com.galati.sesizari.clase.Adresa;
import com.galati.sesizari.clase.Institutie;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Prioritate;
import com.galati.sesizari.repos.InstitutieRepo;
import com.galati.sesizari.service.SesizareService;
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

    @Autowired
    private SesizareService sesizareService;

    @Autowired
    private InstitutieRepo institutieRepo;

    @GetMapping("/harta-sesizarilor")
    public String hartaSesizari() {
        return "harta-sesizari";
    }

    @GetMapping("/sesizare/noua")
    public String formular(Model model, HttpSession session) {
        if (session.getAttribute("utilizatorLogat") == null) {
            return "redirect:/login";
        }
        model.addAttribute("institutii", institutieRepo.findAll());
        return "form-sesizare";
    }
    @PostMapping("/sesizare/salveaza")
    public String salveaza(
            @RequestParam String titlu,
            @RequestParam String descriere,
            @RequestParam String prioritate,
            @RequestParam Long institutieId,
            @RequestParam(required = false) String numeStrada,
            @RequestParam(required = false) String numarStrada,
            @RequestParam(required = false) String zona,
            HttpSession session) {

        User user = (User) session.getAttribute("utilizatorLogat");
        if (user == null) {
            return "redirect:/login";
        }

        Institutie institutie = institutieRepo.findById(institutieId).orElse(null);

        Sesizari sesizare = new Sesizari();
        sesizare.setTitlu(titlu);
        sesizare.setDescriere(descriere);
        sesizare.setPrioritate(Prioritate.valueOf(prioritate));
        sesizare.setAdresa(new Adresa(numeStrada, numarStrada, zona));
        sesizare.setUser(user);
        sesizare.setInstitutie(institutie);

        sesizareService.salveazaSesizare(sesizare);
        return "redirect:/";
    }

}

