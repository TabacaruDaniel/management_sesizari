package com.galati.sesizari.service;

import com.galati.sesizari.clase.RaportSesizare;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.repos.RaportSesizareRepo;
import com.galati.sesizari.repos.SesizariRepo;
import com.galati.sesizari.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaportSesizareService {


    @Autowired
    private RaportSesizareRepo raportSesizareRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SesizariRepo sesizariRepo;

    public List<RaportSesizare> getAllRapoarte() {
        return raportSesizareRepo.findAll();
    }


    public RaportSesizare salveazaRaport(Long idSesizare, Long idUser, String context) {
        Sesizari sesizare = sesizariRepo.findById(idSesizare)
                .orElseThrow(() -> new RuntimeException("Sesizarea nu exista"));

        User user = userRepo.findById(idUser)
                .orElseThrow(() -> new RuntimeException("Userul nu exista"));

        RaportSesizare raport = new RaportSesizare();
        raport.setSesizare(sesizare);
        raport.setUserRaportat(user);
        raport.setContext(context);

        return raportSesizareRepo.save(raport);
    }
}