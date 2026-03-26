package com.galati.sesizari.clase;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Adresa {
    private String nume;
    private String numar;
    private String zona;
    private Double latitudine;
    private Double longitudine;
    public Adresa() {}
    public Adresa(String nume, String numar, String zona,Double latitudine,Double longitudine) {
        this.nume = nume;
        this.numar = numar;
        this.zona = zona;
        this.latitudine=latitudine;
        this.longitudine=longitudine;
    }
    public Adresa(Adresa a){
        this.nume = a.getNume();
        this.numar = a.getNumar();
        this.zona = a.getZona();
        this.latitudine = a.getLatitudine();
        this.longitudine = a.getLongitudine();
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
    public Double getLatitudine() {
        return latitudine;
    }
    public void setLatitudine(Double latitudine) {
        this.latitudine = latitudine;
    }
    public Double getLongitudine() {
        return longitudine;
    }
    public void getLongitudine(Double longitudine) {
        this.longitudine = longitudine;
    }
}
