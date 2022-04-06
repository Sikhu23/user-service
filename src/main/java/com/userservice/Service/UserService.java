package com.userservice.Service;


import com.userservice.Const.ConstantFile;
import com.userservice.Enum.BloodGroup;
import com.userservice.Exception.EmailAlreadyExistsException;
import com.userservice.Exception.EnumException;
import com.userservice.Exception.UserIdExistsException;
import com.userservice.Exception.UserNotFoundException;
import com.userservice.Model.User;
import com.userservice.Model.UserDTO;
import com.userservice.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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




    public  UserDTO saveUser(User user) {
        boolean flag=false;
        String bg = user.getBloodGroup();
        BloodGroup[] bgs= BloodGroup.values();
        for(BloodGroup bloodGroup:bgs){
            if(String.valueOf(bloodGroup.getGroup()).equals(bg)){
                flag=true;
                break;

            }
        }
        System.out.println(flag+" "+bg);
if(flag){

    User user1 = this.userRepo.findByemail(user.getEmail());
    if(user1!=null){
        throw new EmailAlreadyExistsException(ConstantFile.ERRORCODEEMAIL);

    }
    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    this.userRepo.save(user);
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

else{
    throw new EnumException("Blood Group is enum type and cant have value: "+user.getBloodGroup());
}
    }

    public UserDTO changeDetails(User user,String userId)  {

        boolean flag=false;
        String bg = user.getBloodGroup();
        BloodGroup[] bgs= BloodGroup.values();
        for(BloodGroup bloodGroup:bgs){
            if(String.valueOf(bloodGroup.getGroup()).equals(bg)){
                flag=true;
                break;

            }
        }
        if(flag){

            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

            if(user.getUserID()==null || user.getUserID().equals(userId)) {
                if (userRepo.findById(userId).isPresent()) {

                    User user1 = this.userRepo.findByemail(user.getEmail());
                    if (user1 != null && !user1.getUserID().equals(userId)) {
                        throw new EmailAlreadyExistsException(ConstantFile.ERRORCODEEMAIL);

                    }
                    user.setUserID(userId);

                    this.userRepo.save(user);
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
                } else {
                    throw new UserNotFoundException(ConstantFile.ERRORCODE);
                }
            }
            else{
                if(userRepo.findById(user.getUserID()).isPresent() && userRepo.findById(userId).isPresent()){
                    throw new UserIdExistsException("User ID already exists, " +
                            "please give non existing user id or leave it empty");

                }
                else if(userRepo.findById(user.getUserID()).isPresent() && !userRepo.findById(userId).isPresent()){
                    throw new UserNotFoundException(ConstantFile.ERRORCODE);
                }
                else{
                    if (userRepo.findById(userId).isPresent()) {

                        User user1 = this.userRepo.findByemail(user.getEmail());
                        if (user1 != null && !user1.getUserID().equals(userId)) {
                            throw new EmailAlreadyExistsException(ConstantFile.ERRORCODEEMAIL);

                        }
                        user.setUserID(user.getUserID());
                        userRepo.deleteById(userId);
                        this.userRepo.save(user);
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
                    } else {
                        throw new UserNotFoundException(ConstantFile.ERRORCODE);
                    }
                }
            }
        }
        else{
            throw new EnumException("Blood Group is enum type and cant have value: "+user.getBloodGroup());
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

