package com.galati.sesizari.repos;

import com.galati.sesizari.clase.Atasament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface AtasamentRepo extends JpaRepository<Atasament,Long> {

        // Aduce toate pozele/videoclipurile unei anumite sesizări
        List<Atasament> findBySesizare_NrReclamatie(int nrReclamatie);
}
