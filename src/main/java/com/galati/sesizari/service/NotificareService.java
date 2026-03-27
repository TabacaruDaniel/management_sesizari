package com.galati.sesizari.service;

import com.galati.sesizari.clase.ptmaitarziu.Notificare;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.repos.NotificareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificareService {

    @Autowired
    private NotificareRepo notificareRepo;

    public void creeazaNotificare(String mesaj, User user, Sesizari sesizare) {
        Notificare n = new Notificare(mesaj, user, sesizare);
        notificareRepo.save(n);
    }

    public List<Notificare> getNotificariUser(User user) {
        return notificareRepo.findByUserOrderByDataNotificariiDesc(user);
    }

    public int getNrNecitite(User user) {
        return notificareRepo.countByUserAndIsReadFalse(user);
    }

    public void marcheazaToateCaCitite(User user) {
        List<Notificare> necitite = notificareRepo.findByUserAndIsReadFalse(user);
        necitite.forEach(n -> n.setIsRead(true));
        notificareRepo.saveAll(necitite);
    }
}