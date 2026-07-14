package com.galati.sesizari.clase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "poza_sesizare")
public class PozaSesizare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeFisier;

    private String tipFisier;

    @Lob
    @Column(columnDefinition = "LONGBLOB", nullable = false)
    private byte[] continutImagine;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_sesizare", nullable = false, unique = true)
    private Sesizari sesizare;

    public Long getId() {
        return id;
    }

    public String getNumeFisier() {
        return numeFisier;
    }

    public void setNumeFisier(String numeFisier) {
        this.numeFisier = numeFisier;
    }

    public String getTipFisier() {
        return tipFisier;
    }

    public void setTipFisier(String tipFisier) {
        this.tipFisier = tipFisier;
    }

    public byte[] getContinutImagine() {
        return continutImagine;
    }

    public void setContinutImagine(byte[] continutImagine) {
        this.continutImagine = continutImagine;
    }

    public Sesizari getSesizare() {
        return sesizare;
    }

    public void setSesizare(Sesizari sesizare) {
        this.sesizare = sesizare;
    }
}