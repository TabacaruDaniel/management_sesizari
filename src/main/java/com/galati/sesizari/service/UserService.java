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
        userRepo.save(user);
    }
    public User loginUser(User user){
        User userGasit=userRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(userGasit!=null){
            return userGasit;
       }
        else return null;
    }
}
