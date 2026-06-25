package com.galati.sesizari.controller;
import com.galati.sesizari.clase.Institutie;
import com.galati.sesizari.dto.SesizareRequest;
import com.galati.sesizari.repos.InstitutieRepo;
import com.galati.sesizari.service.EmailService;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Status;
import com.galati.sesizari.repos.SesizariRepo;
import com.galati.sesizari.service.SesizareService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sesizari") // Toate rutele de aici vor începe cu /api
public class SesizariRestController {

    @Autowired
    private SesizareService sesizariService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private InstitutieRepo institutieRepo;
    @GetMapping("/toate")
    public List<Sesizari> getToateSesizarile() {
        return sesizariService.gasesteToate(); // Sau cum se numește metoda ta de extragere totală
    }

    @PostMapping("/salveaza")
    public ResponseEntity<String> salveazaJson(@RequestBody SesizareRequest request,
                                               HttpSession session) {

        User userLogat = (User) session.getAttribute("utilizatorLogat");

        if (userLogat == null) {
            return ResponseEntity.status(401).body("Trebuie sa fii logat!");
        }

        try {
            Sesizari sesizare = new Sesizari();

            sesizare.setTitlu(request.getTitlu());
            sesizare.setDescriere(request.getDescriere());
            sesizare.setPrioritate(request.getPrioritate());
            sesizare.setAdresa(request.getAdresa());

            sesizare.setNr_aprecieri(0);
            sesizare.setUser(userLogat);
            sesizare.setDataDepunerii(LocalDate.now());
            sesizare.setStatus(Status.NOU);
            System.out.println("am primit id-ul urmator " +request.getInstitutieId());
            Institutie institutie = institutieRepo.findById(request.getInstitutieId())
                    .orElseThrow(() -> new RuntimeException("Institutia nu exista"));

            sesizare.setInstitutie(institutie);
            System.out.println("am primit institutia urmatoare " +institutie.getNumeInstitutie());
            System.out.println("institutia pusa pe sesizare = "
                    + sesizare.getInstitutie().getNumeInstitutie());
            sesizariService.salveazaSesizare(sesizare);
            emailService.trimiteConfirmare(userLogat.getEmail(), sesizare.getTitlu());

            return ResponseEntity.ok("Sesizare salvata cu succes!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Eroare la baza de date: " + e.getMessage());
        }
    }
}