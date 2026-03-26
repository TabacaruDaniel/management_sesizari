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

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public User(String username,String password,String nume,String prenume,String  email) {
        this.username = username;
        this.password = password;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
    }


}
