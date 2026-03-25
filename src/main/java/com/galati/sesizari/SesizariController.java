package com.galati.sesizari;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v0.1/home")
public class SesizariController {

    @GetMapping("buna")
    public String buna() {
        return "Buna";
    }

}
