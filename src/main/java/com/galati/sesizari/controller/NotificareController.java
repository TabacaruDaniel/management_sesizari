package com.galati.sesizari.controller;

import com.galati.sesizari.clase.User;
import com.galati.sesizari.clase.ptmaitarziu.Notificare;
import com.galati.sesizari.service.NotificareService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificari")
public class NotificareController {

    @Autowired
    private NotificareService notificareService;

    @GetMapping("/toate")
    public List<Notificare> getToate(HttpSession session) {
        User user = (User) session.getAttribute("utilizatorLogat");
        if (user == null) return List.of();
        return notificareService.getNotificariUser(user);
    }

    @GetMapping("/numar")
    public int getNumar(HttpSession session) {
        User user = (User) session.getAttribute("utilizatorLogat");
        if (user == null) return 0;
        return notificareService.getNrNecitite(user);
    }

    @PostMapping("/citesteToate")
    public ResponseEntity<String> citesteToate(HttpSession session) {
        User user = (User) session.getAttribute("utilizatorLogat");
        if (user == null) return ResponseEntity.status(401).body("Neautentificat");
        notificareService.marcheazaToateCaCitite(user);
        return ResponseEntity.ok("ok");
    }
}