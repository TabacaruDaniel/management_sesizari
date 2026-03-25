package com.galati.sesizari;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Categorie {
    @Id
    private int cat_id;
    private String numeInstitutie;

}
