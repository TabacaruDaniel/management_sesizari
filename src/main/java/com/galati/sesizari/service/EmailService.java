package com.galati.sesizari.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    // Metoda pentru sesizare noua
    public void trimiteConfirmare(String catre, String titluSesizare) {
        if (mailSender == null) return;
        SimpleMailMessage mesaj = new SimpleMailMessage();
        mesaj.setTo(catre);
        mesaj.setSubject("Confirmare Sesizare: " + titluSesizare);
        mesaj.setText("Bună ziua,\n\nSesizarea dumneavoastră a fost înregistrată.\n\nO zi bună!");
        mailSender.send(mesaj);
    }

    // METODA CARE ÎȚI DĂ EROARE - ASIGURĂ-TE CĂ E SCRISĂ EXACT AȘA:
    public void trimiteNotificareStatus(String emailDestinatar, String titluSesizare, String noulStatus) {
        if (mailSender == null) {
            System.out.println("LOG: Mailul nu s-a trimis (lipsă config), dar statusul este: " + noulStatus);
            return;
        }
        SimpleMailMessage mesaj = new SimpleMailMessage();
        mesaj.setTo(emailDestinatar);
        mesaj.setSubject("Actualizare status sesizare: " + titluSesizare);
        mesaj.setText("Bună ziua,\n\nStatusul sesizării '" + titluSesizare + "' a fost modificat în: " + noulStatus);
        mailSender.send(mesaj);
    }
}