package com.galati.sesizari.clase;

import jakarta.persistence.*;

@Entity
public class Atasament {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int atasament_id;

    private String tipAtasament;//poza,video
    private String urlAtasament;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sesizare_id")
    private Sesizari sesizare;

}
