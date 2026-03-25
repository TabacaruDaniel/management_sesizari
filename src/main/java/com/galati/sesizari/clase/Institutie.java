package com.galati.sesizari.clase;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Institutie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int institutie_id;
    private String numeInstitutie;
    private String adresa;
    private String emailContact;


}
