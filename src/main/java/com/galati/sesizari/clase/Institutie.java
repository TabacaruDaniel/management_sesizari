package com.galati.sesizari.clase;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Institutie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long institutie_id;
    private String numeInstitutie;
    private String adresa;
    private String emailContact;

    public Institutie() {
    }

    public Institutie(Long institutie_id, String numeInstitutie, String adresa, String emailContact) {
        this.institutie_id = institutie_id;
        this.numeInstitutie = numeInstitutie;
        this.adresa = adresa;
        this.emailContact = emailContact;
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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }
}