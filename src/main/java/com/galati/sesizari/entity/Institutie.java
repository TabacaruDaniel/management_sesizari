package com.galati.sesizari.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Institutie {
    @Id
    private int cat_id;
    private String numeInstitutie;


}