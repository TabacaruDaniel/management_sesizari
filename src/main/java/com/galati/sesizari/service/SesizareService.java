package com.galati.sesizari.service;

import com.galati.sesizari.clase.Institutie;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.repos.SesizariRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SesizareService {

    private final SesizariRepo sesizariRepo; // nu static

    // Constructor injection
    public SesizareService(SesizariRepo sesizariRepo) {
        this.sesizariRepo = sesizariRepo;
    }
    public Sesizari gasesteDupaId(Long id) {
        return sesizariRepo.findById(id).orElse(null);
    }
    public Sesizari salveazaSesizare(Sesizari sesizare) {
        return sesizariRepo.save(sesizare);
    }

    public List<Sesizari> gasesteToate() {
        return sesizariRepo.findAll();
    }


    public List<Sesizari> gasesteDupaInstitutie(Institutie ins) {
        return sesizariRepo.findAllByInstitutie(ins);
    }

}