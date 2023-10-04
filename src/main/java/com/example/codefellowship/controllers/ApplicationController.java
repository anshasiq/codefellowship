package com.example.codefellowship.controllers;

import com.example.codefellowship.Repositorys.ApplicationUserRepo;
import com.example.codefellowship.Repositorys.PostRepo;
import com.example.codefellowship.model.ApplicationUser;
import com.example.codefellowship.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ApplicationController {
@Autowired
    ApplicationUserRepo RepoForApp;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    PostRepo postRepo;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserDetailsService userDetailsService;

    @PostMapping("/signup")
    public RedirectView createUser(String username, String password , String firstName , String lastName  , @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate date , String bio ){
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
        return new RedirectView("/");

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

    @GetMapping("/feed")
    public  String getfeed(Principal p , Model b)
    {      if(p != null){
        System.out.println("here");
        String username = p.getName();
        ApplicationUser applicationUser= RepoForApp.findByUsername(username);
        b.addAttribute("username", username);
        Set<ApplicationUser> app2 =  applicationUser.getFollowers();
        System.out.println(app2.toArray());
        b.addAttribute("posts", app2);}
        return "feed.html";
    }

    @GetMapping("/")
    public String getHomePage(Principal p, Model m , Model b){

        if(p != null){
            System.out.println("here");
            String username = p.getName();
            ApplicationUser applicationUser= RepoForApp.findByUsername(username);

            m.addAttribute("username", username);

            m.addAttribute("username", username);
            m.addAttribute("firstName", applicationUser.getFirstName());
            m.addAttribute("lastName", applicationUser.getLastName());
            m.addAttribute("date", applicationUser.getDate());
            m.addAttribute("bio", applicationUser.getBio());
            List <ApplicationUser> applicationUsers = RepoForApp.findAll() ;
            m.addAttribute("all",applicationUsers);


//             Set<ApplicationUser> app2 =  applicationUser.getFollowers();
//            b.addAttribute("posts", app2);
//            System.out.println(app2.contains(applicationUser));
//           List <ApplicationUser> allusers = RepoForApp.findAll();
//            List <Post> pp = null;
//           for(int i=0;i<allusers.size();++i)
//           {
//               if(app2.contains(allusers.get(i))){
//                   System.out.println("X");
////                  List<Post> posts = postRepo.findByUserid (allusers.get(i));
//                   pp= allusers.get(i).getPosts();
//                   System.out.println(pp.size());
////                   b.addAttribute("posts", posts);
//               }
//               b.addAttribute("posts", pp);
//           }
//            Set <Post> posts = postRepo.findByUserid (app2);


        }

        return "index.html";
    }
//////////////////// lab 18 ---------------->
@PostMapping("/follow/{userId}")
public RedirectView Follow(Principal p, @PathVariable Long userId)
{
    if (p == null)
    {
        throw new IllegalArgumentException("User must be logged in to manage other users!");
    }
    ApplicationUser applicationUser = RepoForApp.findByUsername(p.getName());
    ApplicationUser F = RepoForApp.findByid(userId);
    if (applicationUser.getFollowers().contains(F)) {
        applicationUser.getFollowers().remove(F);
        F.getFollowing().remove(applicationUser);
        System.out.println("unfollow");
        // to remove the following if is already followed
    } else {
        applicationUser.getFollowers().add(F);
        F.getFollowing().add(applicationUser);
        System.out.println("test");
    }
//    System.out.println(applicationUser.getFollowers());
//    System.out.println(applicationUser.getFollowing());
//    if (applicationUser.getFollowing().contains(F)) {
//        applicationUser.getFollowing().remove(F);
//        F.getFollowers().remove(applicationUser);
//        // to remove the following if is already followed
//    } else {
//        applicationUser.getFollowing().add(F);
//        F.getFollowers().add(applicationUser);
//        System.out.println("test");
//    }
//System.out.println("we get there");
//    applicationUser.getFollowers().add(F);
    RepoForApp.save(applicationUser);
    RepoForApp.save(F);
    return new RedirectView("/users/" + userId);
}






}
