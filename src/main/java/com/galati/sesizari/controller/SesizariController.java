package com.galati.sesizari.controller;

import com.galati.sesizari.entity.Sesizari;
import com.galati.sesizari.enums.Status;
import com.galati.sesizari.service.SesizariService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sesizari")
public class SesizariController {

    private final SesizariService sesizariService;

    public SesizariController(SesizariService sesizariService) {
        this.sesizariService = sesizariService;
    }

    @PostMapping
    public Sesizari createSesizare(@RequestBody Sesizari sesizare) {
        return sesizariService.createSesizare(sesizare);
    }

    @GetMapping
    public List<Sesizari> getAllSesizari() {
        return sesizariService.getAllSesizari();
    }

    @GetMapping("/{id}")
    public Sesizari getSesizareById(@PathVariable Integer id) {
        return sesizariService.getSesizareById(id);
    }

    @PutMapping("/{id}/status")
    public Sesizari updateStatus(@PathVariable Integer id, @RequestParam Status status) {
        return sesizariService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public String deleteSesizare(@PathVariable Integer id) {
        sesizariService.deleteSesizare(id);
        return "Sesizarea a fost stearsa";
    }
}