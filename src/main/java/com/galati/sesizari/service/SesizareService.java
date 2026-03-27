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

    public Sesizari salveazaSesizare(Sesizari sesizari) {
        return sesizariRepo.save(sesizari);
    }
    public List<Sesizari> gasesteToate() { return sesizariRepo.findAll();}
    public List<Sesizari> gasesteDupaInstitutie(Long institutie){return sesizariRepo.findAllByInstitutie(institutie); }

}
