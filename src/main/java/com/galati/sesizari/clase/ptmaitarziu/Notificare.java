package com.galati.sesizari.clase.ptmaitarziu;

import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Notificare {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int notif_id;

    private String mesaj;
    private Boolean isRead; //daca notificarea a fost citita
    private LocalDate dataNotificarii;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="nrReclamatie")
    private Sesizari sesizare;


}
