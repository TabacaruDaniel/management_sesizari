package com.galati.sesizari.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.galati.sesizari.clase.User;
import com.galati.sesizari.repos.UserRepo;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepo userRepository;

    public DataLoader(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0) {
            userRepository.save(new User("Daniel", "1234"," "," "," "));
            userRepository.save(new User("Giulia", "1234"," "," "," "));
            userRepository.save(new User("Daria", "1234"," "," "," "));
            System.out.println("Utilizatori de test adăugați în DB!");
        }
    }
}