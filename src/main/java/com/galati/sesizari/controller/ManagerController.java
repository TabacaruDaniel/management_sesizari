package com.galati.sesizari.controller;

import com.galati.sesizari.clase.Adresa;
import com.galati.sesizari.clase.Institutie;
import com.galati.sesizari.clase.PozaSesizare;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Prioritate;
import com.galati.sesizari.enums.Rol;
import com.galati.sesizari.enums.Status;
import com.galati.sesizari.repos.InstitutieRepo;
import com.galati.sesizari.repos.PozaSesizareRepo;
import com.galati.sesizari.repos.SesizariRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private SesizariRepo sesizariRepo;

    @Autowired
    private InstitutieRepo institutieRepo;

    @Autowired
    private PozaSesizareRepo pozaSesizareRepo;

    @GetMapping("/dashboard")
    public String dashboardManager(HttpSession session, Model model) {
        User userLogat = (User) session.getAttribute("utilizatorLogat");

        if (userLogat == null) {
            return "redirect:/login";
        }

        if (userLogat.getRol() != Rol.MANAGER) {
            return "redirect:/acces-interzis";
        }

        List<Sesizari> sesizariNerepartizate = sesizariRepo.findByInstitutieIsNull();

        model.addAttribute("sesizari", sesizariNerepartizate);
        model.addAttribute("institutii", institutieRepo.findAll());
        model.addAttribute("prioritati", Prioritate.values());

        return "dashboard-manager";
    }

    @PostMapping("/repartizeaza")
    public String repartizeazaSesizare(@RequestParam Long idSesizare,
                                       @RequestParam List<Long> institutieIds,
                                       @RequestParam Prioritate prioritate) {

        if (institutieIds == null || institutieIds.isEmpty()) {
            return "redirect:/manager/dashboard";
        }

        Sesizari sesizareOriginala = sesizariRepo.findById(idSesizare)
                .orElseThrow(() -> new RuntimeException("Sesizarea nu exista"));

        Optional<PozaSesizare> pozaOriginalaOptional =
                pozaSesizareRepo.findBySesizare_NrReclamatie(sesizareOriginala.getNrReclamatie());

        for (int i = 0; i < institutieIds.size(); i++) {
            Long institutieId = institutieIds.get(i);

            Institutie institutie = institutieRepo.findById(institutieId)
                    .orElseThrow(() -> new RuntimeException("Institutia nu exista"));

            if (i == 0) {
                sesizareOriginala.setInstitutie(institutie);
                sesizareOriginala.setPrioritate(prioritate);
                sesizareOriginala.setStatus(Status.NOU);

                sesizariRepo.save(sesizareOriginala);
            } else {
                Sesizari copie = copiazaSesizare(sesizareOriginala, institutie, prioritate);

                Sesizari copieSalvata = sesizariRepo.save(copie);

                if (pozaOriginalaOptional.isPresent()) {
                    copiazaPoza(pozaOriginalaOptional.get(), copieSalvata);
                }
            }
        }

        return "redirect:/manager/dashboard";
    }

    private Sesizari copiazaSesizare(Sesizari original,
                                     Institutie institutie,
                                     Prioritate prioritate) {
        Sesizari copie = new Sesizari();

        copie.setTitlu(original.getTitlu());
        copie.setDescriere(original.getDescriere());
        copie.setDataDepunerii(original.getDataDepunerii());
        copie.setDataRezolvarii(null);

        copie.setStatus(Status.NOU);
        copie.setPrioritate(prioritate);
        copie.setInstitutie(institutie);

        copie.setUser(original.getUser());

        if (original.getAdresa() != null) {
            Adresa adresaCopie = new Adresa();
            adresaCopie.setNume(original.getAdresa().getNume());
            adresaCopie.setNumar(original.getAdresa().getNumar());
            adresaCopie.setZona(original.getAdresa().getZona());
            adresaCopie.setLatitudine(original.getAdresa().getLatitudine());
            adresaCopie.setLongitudine(original.getAdresa().getLongitudine());

            copie.setAdresa(adresaCopie);
        }

        return copie;
    }

    private void copiazaPoza(PozaSesizare pozaOriginala, Sesizari sesizareCopie) {
        PozaSesizare pozaCopie = new PozaSesizare();

        pozaCopie.setNumeFisier("sesizare_" + sesizareCopie.getNrReclamatie() + ".jpg");
        pozaCopie.setTipFisier(pozaOriginala.getTipFisier());

        byte[] continutCopiat = Arrays.copyOf(
                pozaOriginala.getContinutImagine(),
                pozaOriginala.getContinutImagine().length
        );

        pozaCopie.setContinutImagine(continutCopiat);
        pozaCopie.setSesizare(sesizareCopie);

        pozaSesizareRepo.save(pozaCopie);
    }
}