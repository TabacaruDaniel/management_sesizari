package com.galati.sesizari.service;

import com.galati.sesizari.enums.Rol;
import com.galati.sesizari.enums.Status;
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
        mesaj.setText("Bună ziua,\n\nAm primit sesizarea dumneavoastră și am înregistrat-o în baza noastră de date Cloud\n\tUn reprezentant va va prelua sesizarea si va va trimite un raspuns in decurs de 30 de zile.\n\nO zi bună!");

        // Trimitem efectiv mailul
        mailSender.send(mesaj);

        System.out.println("LOG: Mailul de confirmare a fost trimis către " + catre);
    }
    public void trimiteRaspuns(String catre, String titluSesizare, Status status) {
        // Creăm obiectul care reprezintă mailul
        SimpleMailMessage mesaj = new SimpleMailMessage();

        mesaj.setTo(catre); // Adresa pe care a scris-o userul în formular
        mesaj.setSubject("Update Sesizare: " + titluSesizare);
        mesaj.setText("Bună ziua,\n\nStatusul sesizarii dumneavoastra a fost schimbat in "+status+"\n\tO zi bună!");

        // Trimitem efectiv mailul
        mailSender.send(mesaj);

        System.out.println("LOG: Mailul de confirmare a fost trimis către " + catre);
    }
}
