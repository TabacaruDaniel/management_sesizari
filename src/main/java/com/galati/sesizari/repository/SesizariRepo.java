package com.galati.sesizari.repository;

import com.galati.sesizari.entity.Sesizari;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SesizariRepo extends JpaRepository<Sesizari, Integer> {
}