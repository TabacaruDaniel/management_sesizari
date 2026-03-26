package com.galati.sesizari.controller;
import com.galati.sesizari.clase.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")

public class LoginController {

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        if(user.getUsername().equals("admin") && user.getPassword().equals("1234")) {
            return "Login reușit!";
        } else {
            return "Date greșite!";
        }
    }
}