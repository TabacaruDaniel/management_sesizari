package com.galati.sesizari.clase;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "raport_sesizare")
public class RaportSesizare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sesizare")
    private Sesizari sesizare;

    @ManyToOne
    @JoinColumn(name = "id_user_raportat")
    private User userRaportat;

    @Column(length = 1000, nullable = false)
    private String context;

    private LocalDateTime dataRaportare = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public Sesizari getSesizare() {
        return sesizare;
    }

    public void setSesizare(Sesizari sesizare) {
        this.sesizare = sesizare;
    }

    public User getUserRaportat() {
        return userRaportat;
    }

    public void setUserRaportat(User userRaportat) {
        this.userRaportat = userRaportat;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public LocalDateTime getDataRaportare() {
        return dataRaportare;
    }

    public void setDataRaportare(LocalDateTime dataRaportare) {
        this.dataRaportare = dataRaportare;
    }
}