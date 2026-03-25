package com.galati.sesizari.repos;

import com.galati.sesizari.clase.IstoricStatusSez;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IstoricStatusRepo extends JpaRepository<IstoricStatusSez,Long> {

    public interface IstoricStatusSezRepo extends JpaRepository<IstoricStatusSez, Long> {

        // Aduce istoricul unei sesizări anume, ordonat frumos după dată (de la cel mai vechi la cel mai nou)
        // Atenție: Aici am presupus că ID-ul din Sesizari se numește "nrReclamatie". Dacă l-ai schimbat în "id", modifică și aici.
        List<IstoricStatusSez> findBySesizare_NrReclamatieOrderByDataModificariiAsc(int nrReclamatie);
    }
}
