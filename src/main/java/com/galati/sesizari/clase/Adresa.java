package com.galati.sesizari.clase;

import jakarta.persistence.Embeddable;

@Embeddable
public class Adresa {
    private String nume;
    private String numar;
    private String zona;
    //private double latitudine;
    //private double longitudine;

    public Adresa() {}
    public Adresa(String nume, String numar, String zona) {
        this.nume = nume;
        this.numar = numar;
        this.zona = zona;
    }
    public Adresa(Adresa a){
        this.nume = a.getNume();
        this.numar = a.getNumar();
        this.zona = a.getZona();
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
