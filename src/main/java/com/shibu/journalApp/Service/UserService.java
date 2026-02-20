package com.shibu.journalApp.Service;


import com.shibu.journalApp.Entity.User;
import com.shibu.journalApp.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();






    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean saveNewUser(User user) {

        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }
        catch(Exception e){
            log.error("error occurred for :{}",user.getUserName(),e);
            return false;
        }


    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }


    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    public User getUserById(String id){

        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(String id){
        userRepository.deleteById(id);

    }

    public User findByUserName(String userName){

        return userRepository.findByUserName(userName);
    }

}
