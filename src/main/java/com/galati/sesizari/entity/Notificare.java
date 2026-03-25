package com.galati.sesizari.entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class Notificare {
    @Id
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