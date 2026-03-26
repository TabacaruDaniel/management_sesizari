package com.galati.sesizari.controller;

import com.galati.sesizari.repos.FeedbackRepo;
import com.galati.sesizari.repos.SesizariRepo;
import com.galati.sesizari.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SesizariRepo sesizareRepo;
    @Autowired
    private FeedbackRepo feedbackRepo;

    @GetMapping("/statistici")
    public String statistici(Model model) {
        // trimite datele către Thymeleaf
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("sesizari", sesizareRepo.findAll());
        model.addAttribute("feedbacks", feedbackRepo.findAll());
        return "admin"; // pagina HTML Thymeleaf
    }
}