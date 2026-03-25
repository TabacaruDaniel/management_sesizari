package com.galati.sesizari.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Adresa {
    private String nume;
    private String numar;
    private String zona;

    public Adresa() {}
    public Adresa(String nume, String numar, String zona) {
        this.nume = nume;
        this.numar = numar;
        this.zona = zona;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getNumar() {
        return numar;
    }

    public void setNumar(String numar) {
        this.numar = numar;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}