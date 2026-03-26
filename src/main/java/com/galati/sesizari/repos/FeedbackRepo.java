package com.galati.sesizari.repos;

import com.galati.sesizari.clase.ptmaitarziu.Feedback;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
    /*List<Feedback> findByUser_Id(Long userId);*/
    List<Feedback> findAll();
}
