package com.galati.sesizari.controller;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.InstitutieTip;

import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.enums.Rol;
import com.galati.sesizari.service.SesizareService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class InstitutieController {

    @GetMapping("/institutie/dashboard")
    public String dashboardInstitutie(HttpSession session, Model model) {
        // poți verifica dacă utilizatorul este logat și are rolul corect
        User user = (User) session.getAttribute("utilizatorLogat");
        if (user == null || user.getRol() != Rol.INSTITUTIE) {
            return "redirect:/login"; // dacă nu e logat sau nu e instituție
        }

        // adaugă orice informație vrei în model
        model.addAttribute("user", user);
        return "dashboard-institutie"; // va căuta templates/institutie/dashboard.html
    }
}
