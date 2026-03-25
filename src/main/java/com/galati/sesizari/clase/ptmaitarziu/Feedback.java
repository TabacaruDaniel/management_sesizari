package com.galati.sesizari.clase.ptmaitarziu;

import com.galati.sesizari.clase.Sesizari;
import jakarta.persistence.*;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int feedback_id;

    private int rating; //1-5
    private String comentariu;

    public Feedback() {}
    public Feedback(int id, int rating, String comentariu) {
        this.feedback_id = id;
        this.rating = rating;
        this.comentariu = comentariu;
    }
    @OneToOne
    @JoinColumn(name="sesizare_id")
    private Sesizari sesizare;

}
