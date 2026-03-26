package com.galati.sesizari.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${upload.path}")
    private String uploadPath;

    public String salveazaFisier(MultipartFile fisier) throws IOException {
        if (fisier == null || fisier.isEmpty()) return null;

        // 1. Verificăm din nou mărimea în cod (opțional, pentru siguranță extra)
        // if (fisier.getSize() > 5 * 1024 * 1024) throw new IOException("Fișier prea mare!");

        // 2. Creăm folderul dacă nu există
        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // 3. Generăm un nume unic: UUID + numele original
        String numeFisier = UUID.randomUUID().toString() + "_" + fisier.getOriginalFilename();
        Path pathDestinatie = uploadDir.resolve(numeFisier);

        // 4. Copiem fișierul pe disc
        Files.copy(fisier.getInputStream(), pathDestinatie, StandardCopyOption.REPLACE_EXISTING);

        return numeFisier; // Returnăm doar numele pentru a-l salva în baza de date
    }

}
