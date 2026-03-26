package com.galati.sesizari.controller;

import com.galati.sesizari.clase.Adresa;
import com.galati.sesizari.clase.Institutie;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Prioritate;
import com.galati.sesizari.enums.Status;
import com.galati.sesizari.repos.InstitutieRepo;
import com.galati.sesizari.repos.SesizariRepo; // Importul care lipsea
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/sesizari")
public class SesizariRestController {

    private final SesizariRepo sesizariRepo;
    private final InstitutieRepo institutieRepo;

    // Folosim constructor injection pentru a scăpa de "Field injection is not recommended"
    @Autowired
    public SesizariRestController(SesizariRepo sesizariRepo, InstitutieRepo institutieRepo) {
        this.sesizariRepo = sesizariRepo;
        this.institutieRepo = institutieRepo;
    }

    @PostMapping("/salveaza")
    @SuppressWarnings("unchecked") // Elimină avertismentul cu "Unchecked cast"
    public ResponseEntity<String> salveazaJson(@RequestBody Map<String, Object> payload, HttpSession session) {
        User userLogat = (User) session.getAttribute("utilizatorLogat");
        if (userLogat == null) {
            return ResponseEntity.status(401).body("Trebuie să fii logat!");
        }

        try {
            Sesizari sesizare = new Sesizari();
            sesizare.setTitlu((String) payload.get("titlu"));
            sesizare.setDescriere((String) payload.get("descriere"));

            // Mapare Prioritate
            String prioStr = (String) payload.get("prioritate");
            sesizare.setPrioritate(Prioritate.valueOf(prioStr));

            // Mapare Adresa
            Map<String, Object> adrMap = (Map<String, Object>) payload.get("adresa");
            Adresa adresa = new Adresa();
            adresa.setNume((String) adrMap.get("nume"));
            adresa.setNumar((String) adrMap.get("numar"));
            adresa.setZona((String) adrMap.get("zona"));
            adresa.setLatitudine(Double.parseDouble(adrMap.get("latitudine").toString()));
            adresa.setLongitudine(Double.parseDouble(adrMap.get("longitudine").toString()));
            sesizare.setAdresa(adresa);

            // Mapare Instituție (din obiectul trimis de JS)
            Map<String, Object> instMap = (Map<String, Object>) payload.get("institutie");
            if (instMap != null && instMap.get("institutie_id") != null) {
                Long idInst = Long.valueOf(instMap.get("institutie_id").toString());
                Institutie inst = institutieRepo.findById(idInst).orElse(null);
                sesizare.setInstitutie(inst);
            }

            sesizare.setUser(userLogat);
            sesizare.setStatus(Status.NOU);
            sesizare.setDataDepunerii(LocalDate.now());

            // Salvare finală folosind repository-ul declarat mai sus
            sesizariRepo.save(sesizare);

            return ResponseEntity.ok("Sesizarea a fost salvată cu succes!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Eroare la server: " + e.getMessage());
        }
    }
}