package com.galati.sesizari.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service // Această adnotare îi spune lui Spring că e o clasă de tip "utilitar"
public class EmailService {

    @Autowired
    private JavaMailSender mailSender; // "Motorul" de trimitere oferit de Spring

    public void trimiteConfirmare(String catre, String titluSesizare) {
        // Creăm obiectul care reprezintă mailul
        SimpleMailMessage mesaj = new SimpleMailMessage();

        mesaj.setTo(catre); // Adresa pe care a scris-o userul în formular
        mesaj.setSubject("Confirmare Sesizare: " + titluSesizare);
        mesaj.setText("Bună ziua,\n\nAm primit sesizarea dumneavoastră și am înregistrat-o în baza noastră de date Cloud.\n\nO zi bună!");

        // Trimitem efectiv mailul
        mailSender.send(mesaj);

        System.out.println("LOG: Mailul de confirmare a fost trimis către " + catre);
    }
}