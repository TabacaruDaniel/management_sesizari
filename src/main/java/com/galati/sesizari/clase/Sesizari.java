package com.galati.sesizari.clase;

import com.galati.sesizari.enums.Prioritate;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Sesizari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nrReclamatie; //cheie primara in sql

    private String titlu;
    private String descriere; //descrierea evenimentului
    @Embedded
    private Adresa adresa;
    //adresa la care are loc evenimentul raportat
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user_id; //cheie straina user_id
    @ManyToOne
    @JoinColumn(name="institutie_id")
    private Institutie institutie_id; //cheie straina institutie, determina categoria de care apartine plangerea


    @Column(updatable = false,name="data_sesizarii")
    private LocalDate dataDepunerii;
    @PrePersist
    protected void onCreate() {
        this.dataDepunerii = LocalDate.now();
    }
    @Column(name = "data_rezolvarii")

    private LocalDate dataRezolvarii;
    @PreUpdate
    protected void onUpdate() {
        this.dataRezolvarii = LocalDate.now();
    }

    @Enumerated(EnumType.STRING)
    private Prioritate prioritate;


}
