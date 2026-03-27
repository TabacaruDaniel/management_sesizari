package com.galati.sesizari.repos;

import com.galati.sesizari.clase.ptmaitarziu.Notificare;
import com.galati.sesizari.clase.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificareRepo extends JpaRepository<Notificare, Long> {
    List<Notificare> findByUserOrderByDataNotificariiDesc(User user);
    List<Notificare> findByUserAndIsReadFalse(User user);
    int countByUserAndIsReadFalse(User user);




}
