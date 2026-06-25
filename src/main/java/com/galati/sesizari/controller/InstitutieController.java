package com.galati.sesizari.controller;
import com.galati.sesizari.clase.Institutie;
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
    @Autowired
    private SesizariRepo sesizariRepo;

    private int prioritateRank(com.galati.sesizari.enums.Prioritate prioritate) {
        if (prioritate == null) {
            return 5;
        }

        return switch (prioritate.name()) {
            case "URGENTA" -> 1;
            case "INALTA" -> 2;
            case "MEDIE" -> 3;
            case "SCAZUTA" -> 4;
            case "NESPECIFICATA" -> 5;
            default -> 5;
        };
    }
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

        User userLogat = (User) session.getAttribute("utilizatorLogat");

        if (userLogat == null) {
            return "redirect:/login";
        }

        if (userLogat.getRol() != Rol.INSTITUTIE) {
            return "redirect:/";
        }

        Institutie institutie = userLogat.getInstitutie();

        if (institutie == null) {
            model.addAttribute("eroare", "Acest cont nu are nicio institutie asociata.");
            return "dashboard-institutie";
        }


        List<Sesizari> sesizari = sesizariRepo.findAllByInstitutie(institutie);

        sesizari.sort((s1, s2) -> Integer.compare(
                prioritateRank(s1.getPrioritate()),
                prioritateRank(s2.getPrioritate())
        ));

        model.addAttribute("institutie", institutie);
        model.addAttribute("sesizari", sesizari);
        return "dashboard-institutie";
    }
}
