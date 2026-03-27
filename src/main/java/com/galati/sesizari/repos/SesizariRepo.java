package com.galati.sesizari.repos;

import com.galati.sesizari.clase.Institutie;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Prioritate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SesizariRepo extends JpaRepository<Sesizari,Long> {

    List <Sesizari> findByUser_Username(String username);
    List<Sesizari> findAllByInstitutie(Long institutie_id); //dupa tipul problemei
    List<Sesizari> findByPrioritate(Prioritate prioritate);
    List<Sesizari> findByAdresa_Zona(String zona);
     Sesizari findByTitlu(String titlu);
    @Query("SELECT s FROM Sesizari s LEFT JOIN FETCH s.institutie")
    List<Sesizari> findAllWithInstitutie();
    List<Sesizari> findByUserAndInstitutie(User user, Institutie institutie);

}
