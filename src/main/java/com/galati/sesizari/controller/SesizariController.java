package com.galati.sesizari.controller;

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

}