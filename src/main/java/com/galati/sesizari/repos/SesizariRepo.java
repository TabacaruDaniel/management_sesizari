package com.galati.sesizari.repos;

import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.enums.Prioritate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SesizariRepo extends JpaRepository<Sesizari,Integer> {

    List <Sesizari> findByUser_Username(String username);
    List<Sesizari> findAllByInstitutie(Integer institutie_id); //dupa tipul problemei
    List<Sesizari> findByPrioritate(Prioritate prioritate);
    List<Sesizari> findByAdresa_Zona(String zona);


}
