package com.galati.sesizari.entity;

import com.galati.sesizari.enums.Status;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class IstoricStatusSez {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int nrReclamatie;
    private Status statusVechi;
    private Status statusNou;
    private LocalDate dataStatusNou;
    private String numeModif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNrReclamatie() {
        return nrReclamatie;
    }

    public void setNrReclamatie(int nrReclamatie) {
        this.nrReclamatie = nrReclamatie;
    }

    public Status getStatusVechi() {
        return statusVechi;
    }

    public void setStatusVechi(Status statusVechi) {
        this.statusVechi = statusVechi;
    }

    public Status getStatusNou() {
        return statusNou;
    }

    public void setStatusNou(Status statusNou) {
        this.statusNou = statusNou;
    }

    public LocalDate getDataStatusNou() {
        return dataStatusNou;
    }

    public void setDataStatusNou(LocalDate dataStatusNou) {
        this.dataStatusNou = dataStatusNou;
    }

    public String getNumeModif() {
        return numeModif;
    }

    public void setNumeModif(String numeModif) {
        this.numeModif = numeModif;
    }
}