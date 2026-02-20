package com.shibu.journalApp.controller;

import com.shibu.journalApp.Entity.User;
import com.shibu.journalApp.Repository.UserRepository;
import com.shibu.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();

        User userInDb=userService.findByUserName(userName);

        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveUser(userInDb);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();

        userRepository.deleteByUserName(userName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
