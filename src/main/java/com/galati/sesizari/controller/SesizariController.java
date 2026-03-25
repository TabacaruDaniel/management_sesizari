package com.galati.sesizari.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Controller
@RequestMapping("/v0.1/home")
public class SesizariController {

    @GetMapping("/buna")
    public String returneaza(Model model) {
        return "index";
    }


}