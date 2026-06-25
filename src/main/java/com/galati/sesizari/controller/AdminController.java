package com.galati.sesizari.controller;

import com.galati.sesizari.clase.Institutie;
import com.galati.sesizari.clase.Sesizari;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Rol;
import com.galati.sesizari.enums.Status;
import com.galati.sesizari.repos.FeedbackRepo;
import com.galati.sesizari.repos.InstitutieRepo;
import com.galati.sesizari.repos.SesizariRepo;
import com.galati.sesizari.repos.UserRepo;
import com.galati.sesizari.service.EmailService;
import com.galati.sesizari.service.RaportSesizareService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RaportSesizareService raportSesizareService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SesizariRepo sesizareRepo;
    @Autowired
    private FeedbackRepo feedbackRepo;
    @Autowired
    private InstitutieRepo institutieRepo;
    @Autowired
    private EmailService emailService;

    @GetMapping("/statistici")
    public String statistici(Model model) {
        // trimite datele către Thymeleaf
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("rapoarte", raportSesizareService.getAllRapoarte());
        model.addAttribute("sesizari", sesizareRepo.findAll());
        model.addAttribute("feedbacks", feedbackRepo.findAll());

        model.addAttribute("institutii", institutieRepo.findAll());
        model.addAttribute("roluri", Rol.values());
        return "admin"; // pagina HTML Thymeleaf
    }
    @PostMapping("/adauga-user")
    public String adaugaUser(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String nume,
                             @RequestParam String prenume,
                             @RequestParam String email,
                             @RequestParam Rol rol,
                             @RequestParam(required = false) Long institutieId,
                             RedirectAttributes redirectAttributes) {

        if (userRepo.existsByUsername(username)) {
            redirectAttributes.addFlashAttribute("eroareUser", "Username-ul exista deja.");
            return "redirect:/admin/statistici?tab=utilizatori";
        }

        if (userRepo.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("eroareUser", "Email-ul exista deja.");
            return "redirect:/admin/statistici?tab=utilizatori";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNume(nume);
        user.setPrenume(prenume);
        user.setEmail(email);
        user.setRol(rol);

        if (rol == Rol.INSTITUTIE && institutieId != null) {
            Institutie institutie = institutieRepo.findById(institutieId)
                    .orElseThrow(() -> new RuntimeException("Institutia nu exista"));
            user.setInstitutie(institutie);
        } else {
            user.setInstitutie(null);
        }

        userRepo.save(user);

        redirectAttributes.addFlashAttribute("succesUser", "User adaugat cu succes.");
        return "redirect:/admin/statistici?tab=utilizatori";
    }
    @PostMapping("/sesizari/repartizeaza")
    public String repartizeazaSesizare(@RequestParam Long idSesizare,
                                       @RequestParam Long institutieId) {

        Sesizari sesizare = (Sesizari) sesizareRepo.findById(idSesizare)
                .orElseThrow(() -> new RuntimeException("Sesizarea nu exista"));

        Institutie institutie = institutieRepo.findById(institutieId)
                .orElseThrow(() -> new RuntimeException("Institutia nu exista"));

        sesizare.setInstitutie(institutie);
        sesizare.setStatus(Status.IN_ANALIZA);

        sesizareRepo.save(sesizare);

        if (sesizare.getUser() != null && sesizare.getUser().getEmail() != null) {
            emailService.trimiteNotificareAdmin(
                    sesizare.getUser().getEmail(),
                    "Sesizarea a fost repartizata",
                    "Buna ziua,\n\nSesizarea dumneavoastra cu titlul \""
                            + sesizare.getTitlu()
                            + "\" a fost repartizata catre institutia de: "
                            + institutie.getNumeInstitutie()
                            + ".\n\nO zi buna!"
            );
        }

        return "redirect:/admin/statistici?tab=sesizari";
    }
    @PostMapping("/sesizari/sterge")
    public String stergeSesizare(@RequestParam Long idSesizare,
                                 @RequestParam String motiv) {

        Sesizari sesizare = (Sesizari) sesizareRepo.findById(idSesizare)
                .orElseThrow(() -> new RuntimeException("Sesizarea nu exista"));

        String emailUser = null;
        if (sesizare.getUser() != null) {
            emailUser = sesizare.getUser().getEmail();
        }

        String titluSesizare = sesizare.getTitlu();

        if (emailUser != null) {
            emailService.trimiteNotificareAdmin(
                    emailUser,
                    "Sesizarea a fost stearsa",
                    "Buna ziua,\n\nSesizarea dumneavoastra cu titlul \""
                            + titluSesizare
                            + "\" a fost stearsa de catre administrator.\n\n"
                            + "Motivul stergerii:\n"
                            + motiv
                            + "\n\nO zi buna!"
            );
        }

        sesizareRepo.delete(sesizare);

        return "redirect:/admin/statistici?tab=sesizari";
    }
    @PostMapping("/sesizari/rezolva")
    public String rezolvaSesizare(@RequestParam Long idSesizare,
                                  @RequestParam String mesaj) {

        Sesizari sesizare = (Sesizari) sesizareRepo.findById(idSesizare)
                .orElseThrow(() -> new RuntimeException("Sesizarea nu exista"));

        String emailUser = null;
        if (sesizare.getUser() != null) {
            emailUser = sesizare.getUser().getEmail();
        }

        String titluSesizare = sesizare.getTitlu();

        sesizare.setStatus(Status.REZOLVAT);
        sesizare.setDataRezolvarii(LocalDate.now());
        sesizareRepo.save(sesizare);

        if (emailUser != null) {
            emailService.trimiteNotificareAdmin(
                    emailUser,
                    "Sesizarea a fost rezolvata",
                    "Buna ziua,\n\nSesizarea dumneavoastra cu titlul \""
                            + titluSesizare
                            + "\" a fost marcata ca rezolvata.\n\n"
                            + "Mesaj administrator:\n"
                            + mesaj
                            + "\n\nO zi buna!"
            );
        }

        sesizareRepo.delete(sesizare);

        return "redirect:/admin/statistici";
    }
    @PostMapping("/sterge-user")
    public String stergeUser(@RequestParam Long idUser,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        User userLogat = (User) session.getAttribute("utilizatorLogat");

        if (userLogat != null && userLogat.getId().equals(idUser)) {
            redirectAttributes.addFlashAttribute("eroareUser", "Nu iti poti sterge propriul cont.");
            return "redirect:/admin/statistici?tab=utilizatori";
        }

        User user = userRepo.findById(idUser)
                .orElseThrow(() -> new RuntimeException("Userul nu exista"));

        if (user.getRol() != Rol.INSTITUTIE) {
            redirectAttributes.addFlashAttribute("eroareUser", "Poti sterge doar conturi de institutie.");
            return "redirect:/admin/statistici?tab=utilizatori";
        }

        userRepo.delete(user);

        redirectAttributes.addFlashAttribute("succesUser", "Contul institutiei a fost sters cu succes.");
        return "redirect:/admin/statistici?tab=utilizatori";
    }
}
