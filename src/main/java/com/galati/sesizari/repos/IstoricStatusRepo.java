package com.galati.sesizari.repos;

import com.galati.sesizari.clase.IstoricStatusSez;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IstoricStatusRepo extends JpaRepository<IstoricStatusSez, Long> {

        List<IstoricStatusSez> findBySesizare_NrReclamatieOrderByDataModificariiAsc(int nrReclamatie);
    }

