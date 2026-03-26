package com.galati.sesizari.clase;

import com.galati.sesizari.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class IstoricStatusSez {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long istoric_id;


    @ManyToOne
    @JoinColumn(name = "sesizare_id")
    private Sesizari sesizare;

    @Enumerated(EnumType.STRING) // Important ca să salveze text (ex: "REZOLVAT") nu cifre (0,1,2)
    private Status statusVechi;
    @Enumerated(EnumType.STRING)
    private Status statusNou;

    private LocalDate dataModificarii;
    private String numeModificator;

    public Long getIstoric_id() {
        return istoric_id;
    }

    public void setIstoric_id(Long istoric_id) {
        this.istoric_id = istoric_id;
    }

    public Sesizari getSesizare() {
        return sesizare;
    }

    public void setSesizare(Sesizari sesizare) {
        this.sesizare = sesizare;
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

    public LocalDate getDataModificarii() {
        return dataModificarii;
    }

    public void setDataModificarii(LocalDate dataModificarii) {
        this.dataModificarii = dataModificarii;
    }

    public String getNumeModificator() {
        return numeModificator;
    }

    public void setNumeModificator(String numeModificator) {
        this.numeModificator = numeModificator;
    }
}