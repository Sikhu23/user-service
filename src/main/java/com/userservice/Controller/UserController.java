package com.userservice.Controller;


import com.userservice.Model.User;
import com.userservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers(){
        return  new ResponseEntity<>(userService.showAllUsers(), HttpStatus.ACCEPTED);
    }


    @GetMapping("/users/{userId}")
    public ResponseEntity<User> findByID(@PathVariable("userId") String userId){
        return new ResponseEntity<>(userService.findByID(userId),HttpStatus.ACCEPTED);
    }


    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user){
        return new ResponseEntity<>(userService.saveUser(user),HttpStatus.ACCEPTED);
    }


    @PutMapping("/users/{userId}")
    public ResponseEntity<User> changeDetails(@Valid @RequestBody User user, @PathVariable("userId")  String userId) throws Exception {
        return new ResponseEntity<>(userService.changeDetails(user,userId),HttpStatus.ACCEPTED);
    }



    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") String userId){
        return new ResponseEntity<>(userService.deleteUserById(userId),HttpStatus.ACCEPTED);
    }


    }
