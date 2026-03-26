package com.galati.sesizari.service;

import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Rol;
import com.galati.sesizari.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public void registerUser(User user) {
        user.setRol(Rol.USER);
        userRepo.save(user);
        if(user.getUsername().equals("admin") && user.getPassword().equals("miaunel123")) {
            User newUser = new User();
            newUser.setRol(Rol.ADMIN);
        }
        if(user.getUsername().equals("Institutie") && user.getPassword().equals("miaunel123**")) {
            User newUser = new User();
            newUser.setRol(Rol.INSTITUTIE);
        }
    }
}
