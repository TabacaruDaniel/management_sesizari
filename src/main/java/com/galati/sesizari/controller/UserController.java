package com.galati.sesizari.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.galati.sesizari.dto.UserDataTransfer;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/")
    public String home() { return "index"; }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request,
                            HttpSession session,
                            Model model) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (jakarta.servlet.http.Cookie c : cookies) {
                if (c.getName().equals("rememberedUser")) {

                    String usernameDinCookie = c.getValue();

                    User user = userRepo.findByUsername(usernameDinCookie);

                    if (user == null) {
                        return "login";
                    }

                    if (user.isBanat()) {
                        model.addAttribute("eroare", "Contul dumneavoastra a fost blocat.");
                        return "login";
                    }

                    session.setAttribute("utilizatorLogat", user);
                    return "redirect:/";
                }
            }
        }

        return "login";
    }
    @PostMapping("/login")
    public String proceseazaLogin(@ModelAttribute User user,
                                  @RequestParam(value="rememberMe", required = false) String rememberMe,
                                  HttpSession session,
                                  HttpServletResponse response,
                                  Model model) {
        User userLogat = userService.loginUser(user);

        if (userLogat != null) {
            session.setAttribute("utilizatorLogat", userLogat);

            // Creează cookie dacă utilizatorul a bifat rememberMe
            if (rememberMe != null) {
                Cookie cookie = new Cookie("rememberedUser", userLogat.getUsername());
                cookie.setMaxAge(60 * 60 * 24 * 7); // 7 zile
                cookie.setPath("/");
                response.addCookie(cookie);
            }

            // Redirecționare în funcție de rol
            if (userLogat.getRol() == Rol.USER) {
                return "redirect:/sesizare/noua";
            } else if (userLogat.getRol() == Rol.INSTITUTIE) {
                return "redirect:/institutie/dashboard";
            } else if (userLogat.getRol() == Rol.ADMIN) {
                return "redirect:/admin/statistici";
            } else {
                model.addAttribute("error", "Acces interzis!");
                return "login";
            }
        } else {
            if (!userService.verificaUsername(user.getUsername())) {
                model.addAttribute("error", "Username-ul nu există!");
            } else {
                model.addAttribute("error", "Parolă incorectă!");
            }
            return "login";
        }
    }
    @GetMapping("/register")
    public String register() { return "register"; }

    @PostMapping("/register")
    public String proceseazaRegister(@ModelAttribute UserDataTransfer userDto, Model model) {
        try {
            // Trimitem DTO-ul către service
            userService.registerUser(userDto);
            return "redirect:/login";
        } catch (Exception e) {
            // Dacă apare eroarea de email duplicat, o afișăm în pagină
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        Cookie cookie = new Cookie("rememberedUser", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/login";
    }
}