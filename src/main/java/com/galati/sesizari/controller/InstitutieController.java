package com.galati.sesizari.controller;

import com.galati.sesizari.clase.Institutie;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.service.EmailService;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.enums.Rol;
import com.galati.sesizari.enums.Status;
import com.galati.sesizari.repos.SesizariRepo;
import com.galati.sesizari.repos.InstitutieRepo;
import com.galati.sesizari.service.RaportSesizareService;
import com.galati.sesizari.service.SesizareService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class InstitutieController {

    private final SesizareService sesizareService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private RaportSesizareService raportSesizareService;

    @Autowired
    private SesizariRepo sesizariRepo;

    @Autowired
    private InstitutieRepo institutieRepo;

    public InstitutieController(SesizareService sesizareService) {
        this.sesizareService = sesizareService;
    }

    private int prioritateRank(com.galati.sesizari.enums.Prioritate prioritate) {
        if (prioritate == null) return 5;
        return switch (prioritate.name()) {
            case "URGENTA" -> 1;
            case "INALTA" -> 2;
            case "MEDIE" -> 3;
            case "SCAZUTA" -> 4;
            default -> 5;
        };
    }

    @PostMapping("/institutie/raporteaza")
    public String raporteazaSesizare(@RequestParam Long idSesizare,
                                     @RequestParam Long idUser,
                                     @RequestParam String context) {

        raportSesizareService.salveazaRaport(idSesizare, idUser, context);

        return "redirect:/institutie/dashboard";
    }
    @PostMapping("/sesizari/actualizeaza-status")
    public String actualizeazaStatus(@RequestParam("idSesizare") Long id,
                                     @RequestParam(required = false) Status noulStatus,
                                     @RequestParam(required = false) String actiune,
                                     @RequestParam(required = false) Long nouaInstitutieId) {

        Sesizari sesizare = sesizareService.gasesteDupaId(id);

        if (sesizare == null) {
            return "redirect:/institutie/dashboard";
        }

        if ("redirectioneaza".equals(actiune)) {

            if (nouaInstitutieId == null) {
                return "redirect:/institutie/dashboard";
            }

            Institutie institutiaVeche = sesizare.getInstitutie();

            Institutie nouaInstitutie = institutieRepo.findById(nouaInstitutieId)
                    .orElseThrow(() -> new RuntimeException("Institutia nu exista"));

            sesizare.setInstitutie(nouaInstitutie);
            sesizare.setStatus(Status.IN_ANALIZA);

            sesizareService.salveazaSesizare(sesizare);

            if (sesizare.getUser() != null && sesizare.getUser().getEmail() != null) {
                emailService.trimiteNotificareAdmin(
                        sesizare.getUser().getEmail(),
                        "Sesizarea a fost transferata",
                        "Buna ziua,\n\nSesizarea dumneavoastra cu titlul \""
                                + sesizare.getTitlu()
                                + "\" a fost transferata"
                                + (institutiaVeche != null ? " de la institutia " + institutiaVeche.getNumeInstitutie() : "")
                                + " catre institutia: "
                                + nouaInstitutie.getNumeInstitutie()
                                + ".\n\nO zi buna!"
                );
            }

            return "redirect:/institutie/dashboard";
        }

        if (noulStatus != null) {
            sesizare.setStatus(noulStatus);

            sesizareService.salveazaSesizare(sesizare);

            if (sesizare.getUser() != null && sesizare.getUser().getEmail() != null) {
                emailService.trimiteRaspuns(
                        sesizare.getUser().getEmail(),
                        sesizare.getTitlu(),
                        sesizare.getStatus()
                );
            }
        }

        return "redirect:/institutie/dashboard";
    }

    @GetMapping("/institutie/dashboard")
    public String dashboardInstitutie(HttpSession session, Model model) {

        User userLogat = (User) session.getAttribute("utilizatorLogat");

        if (userLogat == null) {
            System.out.println(session.getAttribute("utilizatorLogat"));
            return "redirect:/login"; }
        if (userLogat.getRol() != Rol.INSTITUTIE) {
            System.out.println(session.getAttribute("utilizatorLogat"));
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

        List<Institutie> toateInstitutiile = institutieRepo.findAll()
                .stream()
                .filter(i -> !i.getInstitutie_id().equals(institutie.getInstitutie_id()))
                .toList();

        model.addAttribute("institutie", institutie);
        model.addAttribute("sesizari", sesizari);
        model.addAttribute("institutii", toateInstitutiile);
        return "dashboard-institutie";
    }
}