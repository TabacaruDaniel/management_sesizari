package com.galati.sesizari.clase;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Notificare {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

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
