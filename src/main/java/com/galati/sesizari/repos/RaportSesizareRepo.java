package com.galati.sesizari.repos;

import com.galati.sesizari.clase.RaportSesizare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaportSesizareRepo extends JpaRepository<RaportSesizare, Long> {
}