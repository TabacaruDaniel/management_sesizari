package com.galati.sesizari.controller;

import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Rol;
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

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String home() { return "index"; }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpSession session) {
        // 1. Verificăm dacă userul are deja un bilet de acces (Cookie)
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (jakarta.servlet.http.Cookie c : cookies) {
                if (c.getName().equals("rememberedUser")) {
                    // 2. Dacă găsim cookie-ul, căutăm userul în DB
                    User userFound = userService.gasesteDupaUsername(c.getValue());
                    if (userFound != null) {
                        // 3. Îl logăm automat în sesiune
                        session.setAttribute("utilizatorLogat", userFound);
                        return "redirect:/sesizare/noua";
                    }
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

        // 1. Verificăm credențialele prin service
        User userLogat = userService.loginUser(user);

        if (userLogat != null) {
            // 2. Salvăm userul în sesiune (foarte important pentru ambele roluri!)
            session.setAttribute("utilizatorLogat", userLogat);

            // 3. Gestionăm Cookie-ul "Remember Me"
            if (rememberMe != null) {
                Cookie cookie = new Cookie("rememberedUser", userLogat.getUsername());
                cookie.setMaxAge(60 * 60 * 24 * 7); // 7 zile
                cookie.setPath("/");
                response.addCookie(cookie);
            }

            // 4. Redirecționăm în funcție de ROL
            if (userLogat.getRol() == Rol.USER) {
                return "redirect:/sesizare/noua";
            } else if (userLogat.getRol() == Rol.INSTITUTIE) {
                return "redirect:/dashboard-institutie";
            } else {
                // Dacă ai și rol de ADMIN, poți adăuga aici o altă rută
                return "redirect:/";
            }

        } else {
            // 5. Mesaje de eroare dacă login-ul eșuează
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
    public String proceseazaRegister(@ModelAttribute User user) {
        user.setRol(Rol.USER);
        userService.registerUser(user);
        return "redirect:/login";
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