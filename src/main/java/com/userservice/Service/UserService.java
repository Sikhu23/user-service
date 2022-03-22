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

    public User findByID(String userId){
        return this.userRepo.findById(userId).get();

    }




    public  User saveUser(User user){
        return this.userRepo.save(user);

    public User changeDetails(User user,String userId) throws Exception {
        if(userRepo.findById(userId).isPresent()){
            return this.userRepo.save(user);
        }
        else{
            throw new Exception("ID doesnot Exist");
        }

    }



}
