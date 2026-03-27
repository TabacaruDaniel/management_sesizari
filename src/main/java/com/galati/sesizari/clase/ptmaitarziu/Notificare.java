package com.galati.sesizari.clase.ptmaitarziu;

import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Notificare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notif_id;

    private String mesaj;
    private Boolean isRead = false;
    private LocalDate dataNotificarii;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="nrReclamatie")
    private Sesizari sesizare;

    public Notificare() {}

    public Notificare(String mesaj, User user, Sesizari sesizare) {
        this.mesaj = mesaj;
        this.user = user;
        this.sesizare = sesizare;
        this.isRead = false;
        this.dataNotificarii = LocalDate.now();
    }

    public Long getNotif_id() { return notif_id; }
    public String getMesaj() { return mesaj; }
    public void setMesaj(String mesaj) { this.mesaj = mesaj; }
    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }
    public LocalDate getDataNotificarii() { return dataNotificarii; }
    public void setDataNotificarii(LocalDate data) { this.dataNotificarii = data; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Sesizari getSesizare() { return sesizare; }
    public void setSesizare(Sesizari sesizare) { this.sesizare = sesizare; }
}
