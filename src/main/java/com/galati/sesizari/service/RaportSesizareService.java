package com.galati.sesizari.service;

import com.galati.sesizari.clase.RaportSesizare;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.repos.RaportSesizareRepo;
import com.galati.sesizari.repos.SesizariRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaportSesizareService {

    @Autowired
    private RaportSesizareRepo raportSesizareRepo;

    @Autowired
    private SesizariRepo sesizariRepo;

    public List<RaportSesizare> getAllRapoarte() {
        return raportSesizareRepo.findAll();
    }

    public RaportSesizare salveazaRaport(Long idSesizare, String mesaj) {
        Sesizari sesizare = sesizariRepo.findById(idSesizare)
                .orElseThrow(() -> new RuntimeException("Sesizarea nu exista"));

        RaportSesizare raport = new RaportSesizare();
        raport.setSesizare(sesizare);
        raport.setMesaj(mesaj);

        return raportSesizareRepo.save(raport);
    }
}