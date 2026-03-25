package com.galati.sesizari.clase;

import com.galati.sesizari.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class IstoricStatusSez {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "sesizare_id")
    private Sesizari sesizare;

    @Enumerated(EnumType.STRING) // Important ca să salveze text (ex: "REZOLVAT") nu cifre (0,1,2)
    private Status statusVechi;

    @Enumerated(EnumType.STRING)
    private Status statusNou;

    private LocalDate dataModificarii;
    private String numeModificator; // Cine a făcut schimbarea (ex: "Admin_Popescu")

}