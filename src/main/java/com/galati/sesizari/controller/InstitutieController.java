package com.galati.sesizari.controller;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.InstitutieTip;
import com.galati.sesizari.service.EmailService;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.enums.Rol;
import com.galati.sesizari.enums.Status;
import com.galati.sesizari.repos.SesizariRepo;
import com.galati.sesizari.service.SesizareService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
@Controller
public class InstitutieController {
    private final SesizareService sesizareService;
    @Autowired
    private EmailService emailService;

    public InstitutieController(SesizareService sesizareService) {
        this.sesizareService = sesizareService;
    }
    @PostMapping("/sesizari/actualizeaza-status")
    public String actualizeazaStatus(@RequestParam("idSesizare") Long id,
                                     @RequestParam("noulStatus") Status noulStatus) {

        // 1. Căutăm sesizarea în bază după ID
        Sesizari sesizare = sesizareService.gasesteDupaId(id);

        if (sesizare != null) {
            // 2. Modificăm statusul
            sesizare.setStatus(noulStatus);

            // 3. Salvăm modificarea
            emailService.trimiteRaspuns(sesizare.getUser().getEmail(),sesizare.getTitlu(),sesizare.getStatus());
            sesizareService.salveazaSesizare(sesizare);
        }

        // 4. Ne întoarcem la dashboard (refresh la pagină)
        return "redirect:/institutie/dashboard";
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
