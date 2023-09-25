package com.example.codefellowship.controllers;

import com.example.codefellowship.Repositorys.ApplicationUserRepo;
import com.example.codefellowship.model.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;

@Controller
public class ApplicationController {
@Autowired
    ApplicationUserRepo RepoForApp;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private HttpServletRequest request;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserDetailsService userDetailsService;

    @PostMapping("/signup")
    public RedirectView createUser(String username, String password , String firstName , String lastName  , Date date , String bio ){
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(username);
        applicationUser.setLocalDate(LocalDate.now());
        String encryptedPassword = passwordEncoder.encode(password);
        applicationUser.setPassword(encryptedPassword);
        applicationUser.setFirstName(firstName);
        applicationUser.setLastName(lastName);
        applicationUser.setDate(date);
        applicationUser.setBio(bio);
//        applicationUser.setLocalDate(LocalDate.now());

        RepoForApp.save(applicationUser);
        authWithHttpServletRequest(username, password  );
        return new RedirectView("/myprofile");

    }
    public void authWithHttpServletRequest(String username, String password){

        try {
            request.login(username, password);
        }catch (ServletException e){
            e.printStackTrace();
        }
    }

    @GetMapping("/logout")
    public String getLogoutpage(){
        return "index.html";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login.html";
    }

    @GetMapping("/signup")
    public String getSignupPage(){
        return "signup.html";
    }



    @GetMapping("/")
    public String getHomePage(Principal p, Model m){

        if(p != null){
            System.out.println("here");
            String username = p.getName();
            ApplicationUser applicationUser= RepoForApp.findByUsername(username);

            m.addAttribute("username", username);

            m.addAttribute("username", username);
            m.addAttribute("firstName", applicationUser.getFirstName());
            m.addAttribute("lastName", applicationUser.getLastName());
            m.addAttribute("dateOfBirth", applicationUser.getDate());
            m.addAttribute("bio", applicationUser.getBio());

        }

        return "index.html";
    }}
