package com.galati.sesizari.repos;
import com.galati.sesizari.clase.Institutie;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.enums.Prioritate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SesizariRepo extends JpaRepository<Sesizari,Long> {

    List <Sesizari> findByUser_Username(String username);
    List<Sesizari> findByInstitutie(Institutie institutie);    List<Sesizari> findByPrioritate(Prioritate prioritate);
    List<Sesizari> findByAdresa_Zona(String zona);


}