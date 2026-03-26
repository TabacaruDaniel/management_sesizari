package com.galati.sesizari.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/sesizare/noua")
    public String formSesizare() {
        return "form-sesizare";
    }
    @GetMapping("/sesizari/harta")
    public String hartaSesizari() {
        return "harta-sesizari";
    }

}