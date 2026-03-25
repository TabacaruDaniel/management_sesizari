package com.galati.sesizari.clase;

import com.galati.sesizari.enums.Rol;
import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    private String username;
    private String password;
    private String nume;
    private String prenume;
    private String email;

    @Enumerated(EnumType.STRING)
    private Rol rol;


}
