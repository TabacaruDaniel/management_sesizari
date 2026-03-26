package com.galati.sesizari.clase;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Institutie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long institutie_id;
    private String numeInstitutie;
    //private String adresa;
    //private String emailContact;

    public Institutie() {
    }

    public Institutie(Long institutie_id, String numeInstitutie) {
        this.institutie_id = institutie_id;
        this.numeInstitutie = numeInstitutie;
    }

    public Long getInstitutie_id() {
        return institutie_id;
    }

    public void setInstitutie_id(Long institutie_id) {
        this.institutie_id = institutie_id;
    }

    public String getNumeInstitutie() {
        return numeInstitutie;
    }

    public void setNumeInstitutie(String numeInstitutie) {
        this.numeInstitutie = numeInstitutie;
    }

}
