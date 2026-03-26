package com.galati.sesizari.service;

import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.repos.SesizariRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SesizareService {
    @Autowired
    private SesizariRepo sesizariRepo;

    public List<Sesizari> findAll() {
        return sesizariRepo.findAll();
    }
    public Sesizari salveazaSesizare(Sesizari sesizari) {
        return sesizariRepo.save(sesizari);
    }



}
