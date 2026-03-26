package com.galati.sesizari.controller;
import com.galati.sesizari.service.EmailService;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Rol;
import com.galati.sesizari.enums.Status;
import com.galati.sesizari.repos.SesizariRepo;
import com.galati.sesizari.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class SesizariController {

    private final SesizariRepo sesizariRepo;
    private final EmailService emailService;

    // Folosim constructor injection (recomandat de IntelliJ în loc de @Autowired pe câmp)
    @Autowired
    public SesizariController(SesizariRepo sesizariRepo, EmailService emailService) {
        this.sesizariRepo = sesizariRepo;
        this.emailService = emailService;
    }

    @GetMapping("/harta-sesizarilor")
    public String hartaSesizari() {
        return "harta-sesizari";
    }

    @GetMapping("/dashboard-institutie")
    public String dashboardInstitutie(HttpSession session, Model model) {
        User utilizatorLogat = (User) session.getAttribute("utilizatorLogat");

        if (utilizatorLogat == null || utilizatorLogat.getRol() != Rol.INSTITUTIE) {
            return "redirect:/login";
        }

        model.addAttribute("numeInstitutie", utilizatorLogat.getInstitutie().getNumeInstitutie());
        List<Sesizari> listaSesizari = sesizariRepo.findByInstitutie(utilizatorLogat.getInstitutie());
        model.addAttribute("sesizari", listaSesizari);

        return "dashboard-institutie";
    }

    @PostMapping("/sesizari/actualizeaza-status")
    public String actualizeazaStatus(@RequestParam("idSesizare") Long idSesizare,
                                     @RequestParam("noulStatus") Status noulStatus) {

        // Conversie sigură la Long pentru findById
        Sesizari sesizare = sesizariRepo.findById(idSesizare)
                .orElseThrow(() -> new IllegalArgumentException("Sesizarea nu exista ID: " + idSesizare));

        sesizare.setStatus(noulStatus);

        if (noulStatus == Status.REZOLVAT) {
            sesizare.setDataRezolvarii(LocalDate.now());
        }

        sesizariRepo.save(sesizare);

        // Trimitem email-ul
        try {
            emailService.trimiteNotificareStatus(
                    sesizare.getUser().getEmail(),
                    sesizare.getTitlu(),
                    noulStatus.toString()
            );
        } catch (Exception e) {
            System.out.println("Eroare la trimitere email: " + e.getMessage());
            // Mergem mai departe chiar daca mail-ul esueaza momentan
        }

        return "redirect:/dashboard-institutie";
    }
}