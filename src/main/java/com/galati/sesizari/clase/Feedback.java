package com.galati.sesizari.clase;

import jakarta.persistence.Id;

import java.time.LocalDate;

public class Feedback {
    @Id
    private String id;

    private int rating; //1-5
    private String comentariu;
    private LocalDate dataRezolvare;

}