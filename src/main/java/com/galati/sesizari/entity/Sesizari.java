package com.galati.sesizari.entity;
import com.galati.sesizari.enums.Status;
import com.galati.sesizari.enums.Prioritate;
import com.galati.sesizari.enums.Rol;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity


public class Sesizari {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nrReclamatie; //cheie primara in sql

    private String titlu;
    private String descriere; //descrierea evenimentului
    @Embedded
    private Adresa adresa; //adresa la care are loc evenimentul raportat
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user_id;
    @ManyToOne
    @JoinColumn(name="cat_id")
    private Institutie institutie_id;
    private LocalDate dataDepunerii;
    private LocalDate dataRezultat;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Enumerated(EnumType.STRING)
    private Prioritate prioritate;

    public Sesizari() {
    }

    public int getNrReclamatie() {
        return nrReclamatie;
    }

    public void setNrReclamatie(int nrReclamatie) {
        this.nrReclamatie = nrReclamatie;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
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

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public Institutie getCategorie_id() {
        return institutie_id;
    }

    public void setCategorie_id(Institutie institutie_id) {
        this.institutie_id = institutie_id;
    }

    public LocalDate getDataDepunerii() {
        return dataDepunerii;
    }

    public void setDataDepunerii(LocalDate dataDepunerii) {
        this.dataDepunerii = dataDepunerii;
    }

    public LocalDate getDataRezultat() {
        return dataRezultat;
    }

    public void setDataRezultat(LocalDate dataRezultat) {
        this.dataRezultat = dataRezultat;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Prioritate getPrioritate() {
        return prioritate;
    }

    public void setPrioritate(Prioritate prioritate) {
        this.prioritate = prioritate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}