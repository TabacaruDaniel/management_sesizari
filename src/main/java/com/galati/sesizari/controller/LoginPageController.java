package com.galati.sesizari.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class LoginPageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // caută login.html în src/main/resources/templates/
    }
}