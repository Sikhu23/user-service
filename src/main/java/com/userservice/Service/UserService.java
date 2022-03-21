package com.userservice.Service;


import com.userservice.Model.User;
import com.userservice.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public  User saveUser(User user){
        return this.userRepo.save(user);
    }



}
