package com.example.codefellowship.Config;

import com.example.codefellowship.Repositorys.ApplicationUserRepo;
import com.example.codefellowship.model.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    ApplicationUserRepo RepoforApp;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = RepoforApp.findByUsername(username);
        if(applicationUser == null){
            System.out.println("User not found "+ username);
            throw new UsernameNotFoundException("user"+ username+ " was not found in the db");
        }
        System.out.println("Found User: "+applicationUser.getUsername());
        return applicationUser;
    }
}
