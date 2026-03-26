package com.galati.sesizari.repos;

import com.galati.sesizari.clase.Institutie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutieRepo extends JpaRepository<Institutie,Long> {
}