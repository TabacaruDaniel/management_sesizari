package com.galati.sesizari.clase;

import jakarta.persistence.*;

@Entity
public class Atasament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int atasament_id;

    private String tipAtasament;//poza,video
    private String urlAtasament;

    public Atasament() {
    }

    public Atasament(int atasament_id, String tipAtasament, String urlAtasament) {
        this.atasament_id = atasament_id;
        this.tipAtasament = tipAtasament;
        this.urlAtasament = urlAtasament;
    }

    public Atasament(Atasament atasament) {
        this.atasament_id = atasament.atasament_id;
        this.tipAtasament = atasament.tipAtasament;
        this.urlAtasament = atasament.urlAtasament;
    }
    public int getAtasament_id() {
        return atasament_id;
    }

    public void setAtasament_id(int atasament_id) {
        this.atasament_id = atasament_id;
    }

    public String getTipAtasament() {
        return tipAtasament;
    }

    public void setTipAtasament(String tipAtasament) {
        this.tipAtasament = tipAtasament;
    }

    public String getUrlAtasament() {
        return urlAtasament;
    }

    public void setUrlAtasament(String urlAtasament) {
        this.urlAtasament = urlAtasament;
    }
}