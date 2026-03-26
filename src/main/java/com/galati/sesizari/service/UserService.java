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

    public Boolean verificaUsername(String username) {
        return userRepo.existsByUsername(username);
    }
    public Boolean verificaEmail(String email) {
        return userRepo.existsByEmail(email);
    }
    public Boolean verificaPassword(String password) {
        return userRepo.existsByPassword(password);
    }


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
    public User gasesteDupaUsername(String username){
        User userGasit=userRepo.findByUsername(username);
        return userGasit;
    }
}
