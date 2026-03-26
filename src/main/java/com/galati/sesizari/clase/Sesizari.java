package com.galati.sesizari.clase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.galati.sesizari.enums.Prioritate;
import com.galati.sesizari.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Sesizari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nrReclamatie; //cheie primara in sql

    @Enumerated(EnumType.STRING)
    private Status status;
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
    @JsonIgnore
    private List<Atasament> dovezi;
    @OneToMany(mappedBy = "sesizare", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
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

    public Sesizari(Long nrReclamatie, String titlu, String descriere, Adresa adresa, User user_id, Institutie institutie_id, List<Atasament> dovezi, LocalDate dataDepunerii, LocalDate dataRezolvarii, Prioritate prioritate, Status status) {
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
        this.status=status;
    }

    public Sesizari() {
    }

    public Long getNrReclamatie() {
        return nrReclamatie;
    }

    public void setNrReclamatie(Long nrReclamatie) {
        this.nrReclamatie = nrReclamatie;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Institutie getInstitutie() {
        return institutie;
    }

    public void setInstitutie(Institutie institutie) {
        this.institutie = institutie;
    }

    public List<Atasament> getDovezi() {
        return dovezi;
    }

    public void setDovezi(List<Atasament> dovezi) {
        this.dovezi = dovezi;
    }

    public List<IstoricStatusSez> getIstoricStatus() {
        return istoricStatus;
    }

    public void setIstoricStatus(List<IstoricStatusSez> istoricStatus) {
        this.istoricStatus = istoricStatus;
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
