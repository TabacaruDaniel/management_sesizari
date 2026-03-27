package com.galati.sesizari.controller;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.InstitutieTip;

import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.enums.Rol;
import com.galati.sesizari.repos.SesizariRepo;
import com.galati.sesizari.service.SesizareService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
@Controller
public class InstitutieController {
    private final SesizareService sesizareService;

    public InstitutieController(SesizareService sesizareService) {
        this.sesizareService = sesizareService;
    }


    @GetMapping("/institutie/dashboard")
    public String dashboardInstitutie(HttpSession session, Model model) {
        // poți verifica dacă utilizatorul este logat și are rolul corect
        User user = (User) session.getAttribute("utilizatorLogat");
        if (user == null || user.getRol() != Rol.INSTITUTIE) {
            return "redirect:/login"; // dacă nu e logat sau nu e instituție
        }


        // Numele instituției pentru afișare în navbar
        model.addAttribute("numeInstitutie", user.getUsername());

        // Preluăm sesizările pentru această instituție
        List<Sesizari> listaSesizari = sesizareService.gasesteDupaInstitutie(user.getInstitutie());

        // Dacă lista e goală, poate fi construită aici sau se afișează mesajul empty-state
        if (listaSesizari == null) {
            listaSesizari = new ArrayList<>();
        }

        model.addAttribute("sesizari", listaSesizari);
        return "dashboard-institutie"; // va căuta templates/institutie/dashboard.html
    }
}
