package com.galati.sesizari.dto;

import com.galati.sesizari.clase.Adresa;
import com.galati.sesizari.enums.Prioritate;

public class SesizareRequest {

    private String titlu;
    private String descriere;
    private Prioritate prioritate;
    private Long institutieId;
    private Adresa adresa;

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

    public Prioritate getPrioritate() {
        return prioritate;
    }

    public void setPrioritate(Prioritate prioritate) {
        this.prioritate = prioritate;
    }

    public Long getInstitutieId() {
        return institutieId;
    }

    public void setInstitutieId(Long institutieId) {
        this.institutieId = institutieId;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }
}