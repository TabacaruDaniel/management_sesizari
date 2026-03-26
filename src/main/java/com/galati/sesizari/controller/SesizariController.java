package com.galati.sesizari.controller;

import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Rol;
import com.galati.sesizari.repos.UserRepo;
import com.galati.sesizari.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class SesizariController {

    @Autowired
    private UserService userService;

    @GetMapping("/harta-sesizarilor")
    public String hartaSesizari(){
        return "harta-sesizari";
    }

}

