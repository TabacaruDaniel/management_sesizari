package com.galati.sesizari.controller;

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

    @GetMapping("/toate")
    public List<Sesizari> getToateSesizarile() {
        return sesizariService.gasesteToate(); // Sau cum se numește metoda ta de extragere totală
    }
    @PostMapping("/salveaza")
    public ResponseEntity<String> salveazaJson(@RequestBody Sesizari sesizare, HttpSession session) {
        User userLogat = (User) session.getAttribute("utilizatorLogat");
        if (userLogat == null) {
            return ResponseEntity.status(401).body("Trebuie să fii logat!");
        }
        try
        {
            sesizare.setUser(userLogat);
            sesizare.setDataDepunerii(LocalDate.now());
            sesizare.setStatus(Status.NOU);
            sesizariService.salveazaSesizare(sesizare);
            emailService.trimiteConfirmare(userLogat.getEmail(), sesizare.getTitlu());
            return ResponseEntity.ok("Sesizare salvată cu succes!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Eroare la baza de date: " + e.getMessage());
        }
    }
}