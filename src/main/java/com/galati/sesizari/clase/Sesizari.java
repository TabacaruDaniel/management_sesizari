package com.galati.sesizari.clase;

import com.galati.sesizari.enums.Prioritate;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Sesizari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nrReclamatie; //cheie primara in sql

    private String titlu;
    private String descriere; //descrierea evenimentului
    @Embedded
    private Adresa adresa;
    //adresa la care are loc evenimentul raportat
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user; //cheie straina user_id
    @ManyToOne
    @JoinColumn(name="institutie_id")
    private Institutie institutie; //cheie straina institutie, determina categoria de care apartine plangerea

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Atasament> dovezi;
    @OneToMany(mappedBy = "sesizare", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IstoricStatusSez> istoricStatus;

    @Column(updatable = false,name="data_sesizarii")
    private LocalDate dataDepunerii;
    @PrePersist
    protected void onCreate() {
        this.dataDepunerii = LocalDate.now();
    }
    @Column(name = "data_rezolvarii")

    private LocalDate dataRezolvarii; //este setata in service

    @Enumerated(EnumType.STRING)
    private Prioritate prioritate;

    public Sesizari(Long nrReclamatie, String titlu, String descriere, Adresa adresa, User user_id, Institutie institutie_id, List<Atasament> dovezi, LocalDate dataDepunerii, LocalDate dataRezolvarii, Prioritate prioritate) {
        this.nrReclamatie = nrReclamatie;
        this.titlu = titlu;
        this.descriere = descriere;
        this.adresa = adresa;
        this.user = user_id;
        this.institutie = institutie_id;
        this.dovezi = dovezi;
        this.dataDepunerii = dataDepunerii;
        this.dataRezolvarii = dataRezolvarii;
        this.prioritate = prioritate;
    }

    public Sesizari() {
    }

    public Long getNrReclamatie() {
        return nrReclamatie;
    }

    public void setNrReclamatie(Long nrReclamatie) {
        this.nrReclamatie = nrReclamatie;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public User getUser_id() {
        return user;
    }

    public void setUser_id(User user_id) {
        this.user = user_id;
    }

    public Institutie getInstitutie_id() {
        return institutie;
    }

    public void setInstitutie_id(Institutie institutie_id) {
        this.institutie = institutie_id;
    }

    public List<Atasament> getDovezi() {
        return dovezi;
    }

    public void setDovezi(List<Atasament> dovezi) {
        this.dovezi = dovezi;
    }

    public LocalDate getDataDepunerii() {
        return dataDepunerii;
    }

    public void setDataDepunerii(LocalDate dataDepunerii) {
        this.dataDepunerii = dataDepunerii;
    }

    public LocalDate getDataRezolvarii() {
        return dataRezolvarii;
    }

    public void setDataRezolvarii(LocalDate dataRezolvarii) {
        this.dataRezolvarii = dataRezolvarii;
    }

    public Prioritate getPrioritate() {
        return prioritate;
    }

    public void setPrioritate(Prioritate prioritate) {
        this.prioritate = prioritate;
    }
}