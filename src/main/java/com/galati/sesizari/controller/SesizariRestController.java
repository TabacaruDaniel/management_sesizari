package com.galati.sesizari.controller;

import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Status;
import com.galati.sesizari.service.SesizareService;
import com.galati.sesizari.service.EmailService; // Importă serviciul de email
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/sesizari")
public class SesizariRestController {

    @Autowired
    private SesizareService sesizariService;

    @Autowired
    private EmailService emailService; // 1. Injectăm serviciul de email aici

    @PostMapping("/salveaza")
    public ResponseEntity<String> salveazaJson(@RequestBody Sesizari sesizare, HttpSession session) {
        User userLogat = (User) session.getAttribute("utilizatorLogat");

        if (userLogat == null) {
            return ResponseEntity.status(401).body("Trebuie să fii logat!");
        }

        try {
            // Setăm datele automate
            sesizare.setUser(userLogat);
            sesizare.setDataDepunerii(LocalDate.now());
            sesizare.setStatus(Status.NOU);

            // 2. SALVĂM ÎN BAZA DE DATE
            sesizariService.salveazaSesizare(sesizare);

            // 3. APELĂM FUNCȚIA DE EMAIL
            // Trimitem la emailul userului din sesiune și titlul sesizării din JSON
            emailService.trimiteConfirmare(userLogat.getEmail(), sesizare.getTitlu());

            return ResponseEntity.ok("Sesizare salvată cu succes și mail trimis!");
        } catch (Exception e) {
            e.printStackTrace(); // Afișează eroarea în consola IntelliJ dacă pică mailul
            return ResponseEntity.status(500).body("Eroare la procesare: " + e.getMessage());
        }
    }
}