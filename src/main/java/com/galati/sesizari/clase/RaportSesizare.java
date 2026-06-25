package com.galati.sesizari.clase;

import jakarta.persistence.*;

@Entity
@Table(name = "raport_sesizare")
public class RaportSesizare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK catre tabela sesizari
    @ManyToOne
    @JoinColumn(name = "id_sesizare")
    private Sesizari sesizare;

    @Column(nullable = false, length = 1000)
    private String mesaj;

    public RaportSesizare() {
    }

    public RaportSesizare(Sesizari sesizare, String mesaj) {
        this.sesizare = sesizare;
        this.mesaj = mesaj;
    }

    public Long getId() {
        return id;
    }

    public Sesizari getSesizare() {
        return sesizare;
    }

    public void setSesizare(Sesizari sesizare) {
        this.sesizare = sesizare;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }
}