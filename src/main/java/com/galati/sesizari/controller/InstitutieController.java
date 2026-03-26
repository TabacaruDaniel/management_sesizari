package com.galati.sesizari.controller;

import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.service.SesizareService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class InstitutieController {

    private final SesizareService sesizareService;

    public InstitutieController(SesizareService sesizareService) {
        this.sesizareService = sesizareService;
    }

    @GetMapping("/institutie/dashboard")
    public String afiseazaDashboard(Model model) {
        // 1. Simulăm numele instituției logate (ulterior îl vei lua din Spring Security)
        String numeInstitutieLogata = "Serviciul Salubritate";

        // 2. Luăm lista de sesizări din baza de date pentru această instituție
        List<Sesizari> listaSesizari = sesizareService.gasesteDupaInstitutie(numeInstitutieLogata);

        // 3. Trimitem datele către HTML prin intermediul obiectului Model
        model.addAttribute("numeInstitutie", numeInstitutieLogata);
        model.addAttribute("sesizari", listaSesizari);

        // 4. Returnăm numele fișierului .html din src/main/resources/templates
        return "dashboard-institutie";
    }
}
