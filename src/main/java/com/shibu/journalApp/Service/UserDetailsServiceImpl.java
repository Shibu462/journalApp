package com.shibu.journalApp.Service;

import com.shibu.journalApp.Entity.User;
import com.shibu.journalApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



        User user = userRepository.findByUserName(username);



        if(user == null){

            throw new UsernameNotFoundException("User not found");
        }



        String[] rolesArray;
        //this caused error in authentication process as getRoles( )returned null pointer exception
        if(user.getRoles() == null){

            rolesArray = new String[]{"USER"};
        } else {
            rolesArray = user.getRoles().toArray(new String[0]);
        }

        UserDetails springUser =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUserName())
                        .password(user.getPassword())
                        .roles(rolesArray)
                        .build();



        return springUser;
    }

}
