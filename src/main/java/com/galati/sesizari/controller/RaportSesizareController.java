package com.galati.sesizari.controller;

import com.galati.sesizari.service.RaportSesizareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rapoarte")
public class RaportSesizareController {

    @Autowired
    private RaportSesizareService raportSesizareService;

    @PostMapping("/adauga")
    public String adaugaRaport(@RequestParam Long idSesizare,
                               @RequestParam Long idUser,
                               @RequestParam String context) {

        raportSesizareService.salveazaRaport(idSesizare, idUser, context);

        return "redirect:/institutie/dashboard";
    }
}