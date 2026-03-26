package com.galati.sesizari.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // Am lăsat doar harta, formularul se mută exclusiv la SesizariController
    @GetMapping("/sesizari/harta")
    public String hartaSesizari() {
        return "harta-sesizari";
    }

}