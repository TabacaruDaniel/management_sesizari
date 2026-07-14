package com.galati.sesizari.repos;

import com.galati.sesizari.clase.PozaSesizare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PozaSesizareRepo extends JpaRepository<PozaSesizare, Long> {

    Optional<PozaSesizare> findBySesizare_NrReclamatie(Long nrReclamatie);
}