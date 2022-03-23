package com.userservice.Controller;


import com.userservice.Model.User;
import com.userservice.Model.UserDTO;
import com.userservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping()
    public ResponseEntity<List<UserDTO>> showAllUsers(@QueryParam("page") Integer page,@QueryParam("pageSize") Integer pageSize){
        return  new ResponseEntity<>(userService.showAllUsers(page,pageSize), HttpStatus.ACCEPTED);
    }



    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> findByID(@PathVariable("userId") String userId){
        return new ResponseEntity<>(userService.findByID(userId),HttpStatus.ACCEPTED);
    }


    @PostMapping()
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user){
        return new ResponseEntity<>(userService.saveUser(user),HttpStatus.ACCEPTED);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<User> changeDetails(@Valid @RequestBody User user, @PathVariable("userId")  String userId) throws Exception {
        return new ResponseEntity<>(userService.changeDetails(user,userId),HttpStatus.ACCEPTED);
    }



    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") String userId){
        return new ResponseEntity<>(userService.deleteUserById(userId),HttpStatus.ACCEPTED);
    }


    }
