package com.galati.sesizari.repos;

import com.galati.sesizari.clase.Sesizari;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SesizariRepo extends JpaRepository<Sesizari,Integer> {

Sesizari findByDataDepunerii(String dataDepunerii);

}
