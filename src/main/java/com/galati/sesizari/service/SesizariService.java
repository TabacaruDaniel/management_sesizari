package com.galati.sesizari.service;

import com.galati.sesizari.entity.Sesizari;
import com.galati.sesizari.enums.Status;
import com.galati.sesizari.repository.SesizariRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SesizariService {

    private final SesizariRepo sesizariRepo;

    public SesizariService(SesizariRepo sesizariRepo) {
        this.sesizariRepo = sesizariRepo;
    }

    public Sesizari createSesizare(Sesizari sesizare) {
        sesizare.setDataDepunerii(LocalDate.now());
        sesizare.setStatus(Status.NOU);
        return sesizariRepo.save(sesizare);
    }

    public List<Sesizari> getAllSesizari() {
        return sesizariRepo.findAll();
    }

    public Sesizari getSesizareById(Integer id) {
        return sesizariRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesizarea nu exista"));
    }

    public Sesizari updateStatus(Integer id, Status statusNou) {
        Sesizari sesizare = sesizariRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesizarea nu exista"));

        sesizare.setStatus(statusNou);
        return sesizariRepo.save(sesizare);
    }

    public void deleteSesizare(Integer id) {
        sesizariRepo.deleteById(id);
    }
}