package com.galati.sesizari;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Sesizari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nrReclamatie; //cheie primara in sql

    private String descriere; //descrierea evenimentului
    @Embedded
    private Adresa adresa; //adresa la care are loc evenimentul raportat
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="cat_id")
    private Categorie categorie;
    private LocalDate dataDepunerii;

    public int getNrReclamatie() {

        return nrReclamatie;
    }

    public void setNrReclamatie(int nrReclamatie) {
        this.nrReclamatie = nrReclamatie;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public LocalDate getDataDepunerii() {
        return dataDepunerii;
    }

    public void setDataDepunerii(LocalDate dataDepunerii) {
        this.dataDepunerii = dataDepunerii;
    }
}
