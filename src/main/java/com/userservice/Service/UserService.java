package com.userservice.Service;


import com.userservice.Exception.UserNotFoundException;
import com.userservice.Model.User;
import com.userservice.Model.UserDTO;
import com.userservice.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    public List<UserDTO> showAllUsers(Integer page,Integer pageSize) {
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        Pageable firstPage = PageRequest.of(page-1, pageSize);
        List<User> allUsers=  userRepo.findAll(firstPage).toList();
        if(allUsers.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }
        List<UserDTO> allUsersDTO=new ArrayList<>();
        for(User user:allUsers){
            UserDTO userDTO=new UserDTO(user.getUserID(),user.getFirstName(),user.getMiddleName(),
                    user.getLastName(),user.getPhoneNumber(),user.getDateOfBirth(),user.getGender(),
                    user.getAddress(),user.getEmployeeNumber(),user.getBloodGroup(),user.getEmail());

            allUsersDTO.add(userDTO);

        }
        return  allUsersDTO;

    }

    public UserDTO findByID(String userId){

        try{
            User user=this.userRepo.findById(userId).get();
            UserDTO userDTO=new UserDTO();
            userDTO.setUserID(user.getUserID());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setMiddleName(user.getMiddleName());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setEmail(user.getEmail());
            userDTO.setAddress(user.getAddress());
            userDTO.setDateOfBirth(user.getDateOfBirth());
            userDTO.setEmployeeNumber(user.getEmployeeNumber());
            userDTO.setBloodGroup(user.getBloodGroup());
            userDTO.setGender(user.getGender());
            return  userDTO;
        }

        catch(Exception e){
            throw new UserNotFoundException("User Not Found");
        }


    }

    public UserDTO userByEmail(String email){
        try{
            User user = userRepo.findByemail(email);
            UserDTO userDTO = new UserDTO(user.getUserID(),user.getFirstName(),user.getMiddleName(),
                    user.getLastName(),user.getPhoneNumber(),user.getDateOfBirth(),user.getGender(),
                    user.getAddress(),user.getEmployeeNumber(),user.getBloodGroup(),user.getEmail());

            return  userDTO;
        }
        catch(Exception e){
            throw new UserNotFoundException("User Doesnot Exists");
        }
    }




    public  User saveUser(User user) {
        return this.userRepo.save(user);
    }

    public User changeDetails(User user,String userId) throws Exception {
        if(userRepo.findById(userId).isPresent()){
            user.setUserID(userId);
            return this.userRepo.save(user);
        }
        else{
            throw new UserNotFoundException("User Not Found");
        }

    }

            public String deleteUserById(String userId){
                if(userRepo.findById(userId).isPresent()){

                    userRepo.deleteById(userId);
                    return "User  Successfully Deleted";
                }
                else{
                    throw new UserNotFoundException("User Not Found");
                }

            }




}

