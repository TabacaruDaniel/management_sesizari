package com.galati.sesizari.clase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.galati.sesizari.enums.Rol;
import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String nume;
    private String prenume;
    private String email;
    @ManyToOne
    private Institutie institutie;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public User() {
    }

    public User(String username, String password, String nume, String prenume, String email, Institutie institutie, Rol rol) {
        this.username = username;
        this.password = password;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.institutie = institutie;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Institutie getInstitutie() {
        return institutie;
    }

    public void setInstitutie(Institutie institutie) {
        this.institutie = institutie;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}