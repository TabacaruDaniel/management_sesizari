package com.galati.sesizari.controller;
import com.galati.sesizari.clase.*;
import com.galati.sesizari.enums.Prioritate;
import com.galati.sesizari.enums.Status;
import com.galati.sesizari.repos.InstitutieRepo;
import com.galati.sesizari.repos.PozaSesizareRepo;
import com.galati.sesizari.repos.SesizariRepo;
import com.galati.sesizari.service.EmailService;
import com.galati.sesizari.service.SesizareService;
import com.galati.sesizari.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class SesizariController {
    @Autowired
    private PozaSesizareRepo pozaSesizareRepo;
    @Autowired
    private SesizariRepo sesizariRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private SesizareService sesizareService;

    @Autowired
    private InstitutieRepo institutieRepo;

    private byte[] convertesteInJpeg(MultipartFile poza) throws Exception {
        String originalFilename = poza.getOriginalFilename();

        if (originalFilename == null || originalFilename.isBlank()) {
            originalFilename = "imagine";
        }

        Path inputTemp = Files.createTempFile("upload-", "-" + originalFilename);
        Path outputTemp = Files.createTempFile("converted-", ".jpg");

        // Stergem fisierul gol creat automat, ca ImageMagick sa poata scrie el output-ul
        Files.deleteIfExists(outputTemp);

        poza.transferTo(inputTemp.toFile());

        ProcessBuilder processBuilder = new ProcessBuilder(
                "C:\\Program Files\\ImageMagick-7.1.2-Q16-HDRI\\magick.exe",

                inputTemp.toAbsolutePath().toString(),
                "-auto-orient",
                "-strip",
                "-resize", "1600x1600>",
                "-quality", "85",
                "jpg:" + outputTemp.toAbsolutePath()
        );

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        String output = new String(process.getInputStream().readAllBytes());

        int exitCode = process.waitFor();

        if (exitCode != 0) {
            Files.deleteIfExists(inputTemp);
            Files.deleteIfExists(outputTemp);

            System.out.println("=== EROARE IMAGEMAGICK ===");
            System.out.println(output);

            throw new RuntimeException("ImageMagick nu a putut converti poza. Detalii: " + output);
        }

        byte[] pozaConvertita = Files.readAllBytes(outputTemp);

        Files.deleteIfExists(inputTemp);
        Files.deleteIfExists(outputTemp);

        return pozaConvertita;
    }
    @GetMapping("/harta-sesizarilor")
    public String hartaSesizari() {
        return "harta-sesizari";
    }

    @GetMapping("/sesizare/noua")
    public String formular(Model model, HttpSession session) {
        if (session.getAttribute("utilizatorLogat") == null) {
            return "redirect:/login";
        }
        model.addAttribute("institutii", institutieRepo.findAll());
        return "form-sesizare";
    }
    @GetMapping("/api/sesizari/{id}/poza")
    public ResponseEntity<byte[]> afiseazaPozaSesizare(@PathVariable Long id) {

        Optional<PozaSesizare> pozaOptional =
                pozaSesizareRepo.findBySesizare_NrReclamatie(id);

        if (pozaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PozaSesizare poza = pozaOptional.get();

        String tipFisier = poza.getTipFisier();

        if (tipFisier == null || tipFisier.isBlank() || tipFisier.equals("application/octet-stream")) {
            tipFisier = "image/jpeg";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(tipFisier))
                .header("Content-Disposition", "inline")
                .body(poza.getContinutImagine());
    }
    @PostMapping("/api/sesizari/salveaza-cu-poze")
    public ResponseEntity<?> salveazaSesizareCuPoza(@RequestParam String titlu,
                                                    @RequestParam String descriere,
                                                    @RequestParam(required = false) Prioritate prioritate,
                                                    @RequestParam(required = false) Long institutieId,
                                                    @RequestParam String nume,
                                                    @RequestParam String numar,
                                                    @RequestParam String zona,
                                                    @RequestParam Double latitudine,
                                                    @RequestParam Double longitudine,
                                                    @RequestParam("poza") MultipartFile poza,
                                                    HttpSession session) {
        try {
            if (poza == null || poza.isEmpty()) {
                return ResponseEntity.badRequest().body("Poza este obligatorie.");
            }


            Sesizari sesizare = new Sesizari();

            sesizare.setTitlu(titlu);
            sesizare.setDescriere(descriere);

            sesizare.setStatus(Status.NOU);



            // ASTEA SUNT IMPORTANTE PENTRU HARTA
            Adresa adresa = new Adresa();

            adresa.setNume(nume);
            adresa.setNumar(numar);
            adresa.setZona(zona);
            adresa.setLatitudine(latitudine);
            adresa.setLongitudine(longitudine);

            sesizare.setAdresa(adresa);
            sesizare.setDataDepunerii(LocalDate.now());

            User userLogat = (User) session.getAttribute("utilizatorLogat");
            if (userLogat != null) {
                sesizare.setUser(userLogat);
            }

            Sesizari sesizareSalvata = sesizariRepo.save(sesizare);

try {
    if (userLogat != null && userLogat.getEmail() != null) {
        emailService.trimiteNotificareAdmin(
                userLogat.getEmail(),
                "Sesizarea a fost inregistrata",
                "Buna ziua,\n\n" +
                        "Sesizarea dumneavoastra cu titlul \"" + sesizareSalvata.getTitlu() + "\" a fost inregistrata cu succes.\n\n" +
                        "Numar reclamatie: " + sesizareSalvata.getNrReclamatie() + "\n" +
                        "Status: " + sesizareSalvata.getStatus() + "\n\n" +
                        "Veti fi notificat/a cand statusul sesizarii se modifica.\n\n" +
                        "O zi buna!"
        );
    }
}catch(Exception emailError) {
    System.out.println("Emailul nu a putut fi trimis, dar sesizarea a fost salvata.");
    emailError.printStackTrace();
}
            PozaSesizare pozaSesizare = new PozaSesizare();
            byte[] pozaJpeg;
            try {
                pozaJpeg = convertesteInJpeg(poza);
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.badRequest()
                        .body("Eroare conversie poza: " + e.getMessage());
            }

            pozaSesizare.setNumeFisier("sesizare_" + sesizareSalvata.getNrReclamatie() + ".jpg");
            pozaSesizare.setTipFisier("image/jpeg");
            pozaSesizare.setContinutImagine(pozaJpeg);

            pozaSesizare.setSesizare(sesizareSalvata);

            pozaSesizareRepo.save(pozaSesizare);

            return ResponseEntity.ok("Sesizarea a fost salvata cu poza.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Eroare la salvarea sesizarii: " + e.getMessage());
        }


    }

}