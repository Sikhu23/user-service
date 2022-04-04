package com.userservice.Service;


import com.userservice.Const.ConstantFile;
import com.userservice.Exception.EmailAlreadyExistsException;
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
                    user.getLastName(),user.getPhoneNumber(),user.getDateOfBirth(),user.getGender().toString(),
                    user.getAddress(),user.getEmployeeNumber(),user.getBloodGroup().toString(),user.getEmail());

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
            userDTO.setBloodGroup(user.getBloodGroup().toString());
            userDTO.setGender(user.getGender().toString());
            return  userDTO;
        }

        catch(Exception e){
            throw new UserNotFoundException(ConstantFile.ERRORCODE);
        }


    }

    public User userByEmail(String email){
       if(userRepo.findByemail(email)!=null){
            return userRepo.findByemail(email);


        }
        else{
            throw new UserNotFoundException(ConstantFile.ERRORCODE);
        }
    }




    public  User saveUser(User user) {
        User user1 = this.userRepo.findByemail(user.getEmail());
        if(user1!=null){
            throw new EmailAlreadyExistsException(ConstantFile.ERRORCODEEMAIL);

        }
        return this.userRepo.save(user);
    }

    public User changeDetails(User user,String userId)  {
        if(userRepo.findById(userId).isPresent()){

            User user1 = this.userRepo.findByemail(user.getEmail());
            if(user1!=null && user1.getUserID().equals(userId)){
                throw new EmailAlreadyExistsException(ConstantFile.ERRORCODEEMAIL);

            }
            user.setUserID(userId);
            return this.userRepo.save(user);
        }
        else{
            throw new UserNotFoundException(ConstantFile.ERRORCODE);
        }

    }

            public String deleteUserById(String userId){
                if(userRepo.findById(userId).isPresent()){

                    userRepo.deleteById(userId);
                    return ConstantFile.SUCCESSDELETE;
                }
                else{
                    throw new UserNotFoundException(ConstantFile.ERRORCODE);
                }

            }




}

