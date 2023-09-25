package com.example.codefellowship.controllers;

import com.example.codefellowship.Repositorys.ApplicationUserRepo;
import com.example.codefellowship.Repositorys.PostRepo;
import com.example.codefellowship.model.ApplicationUser;
import com.example.codefellowship.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PostController {

    @Autowired
    PostRepo postRepo;
    @Autowired
    ApplicationUserRepo applicationUserRepo;
    @PostMapping("/addpost")
    public RedirectView addpost(Principal principal , String body ){
        if (principal != null) {
            String username = principal.getName();
            ApplicationUser applicationUser = applicationUserRepo.findByUsername(username);

            Post post = new Post( body, LocalDate.now() ,applicationUser);

            postRepo.save(post);
        }

    return new RedirectView("/myprofile");
    }
    @GetMapping("/myprofile")
    public String getmyprofile(Principal p, Model m){
        if(p != null){
            System.out.println("here");
            String username = p.getName();
            ApplicationUser applicationUser= applicationUserRepo.findByUsername(username);

            m.addAttribute("username", username);

            m.addAttribute("username", username);
            m.addAttribute("firstName", applicationUser.getFirstName());
            m.addAttribute("lastName", applicationUser.getLastName());
//            m.addAttribute("dateOfBirth", applicationUser.getDate());
            m.addAttribute("bio", applicationUser.getBio());
//            List<Post> posts = postRepo.findByUserid (applicationUser);
//            m.addAttribute("myposts", posts);
            m.addAttribute("picture","https://cdn3.iconfinder.com/data/icons/vector-icons-6/96/256-512.png");

        }
        return "myprofile.html";}

//    @GetMapping("/getposts")
//    public String getposts( Principal p ,Module m){
//        if (p != null) {
//            String username = p.getName();
//            ApplicationUser applicationUser = applicationUserRepo.findByUsername(username);
////            m.addAttribute("posts", posts);
//        }
//        return null;
//    }
    @GetMapping("/users/{id}")
    public String getUserInfo(Model m, Principal p, @PathVariable Long id)
    {
        if (p != null)  // not strictly required if your WebSecurityConfig is correct
        {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepo.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("firstName", applicationUser.getFirstName());
            m.addAttribute("lastName", applicationUser.getLastName());
//            m.addAttribute("dateOfBirth", applicationUser.getDate());
            m.addAttribute("bio", applicationUser.getBio());
//            List<Post> posts = postRepo.findByUserid (applicationUser);
//            m.addAttribute("myposts", posts);
            m.addAttribute("picture","https://cdn3.iconfinder.com/data/icons/vector-icons-6/96/256-512.png");

        }

        ApplicationUser applicationUser = applicationUserRepo.findById(id)
                // we use the ResourceNotFoundException that we wrote it down
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id "+id));

        m.addAttribute("username", applicationUser.getUsername());
        m.addAttribute("firstName", applicationUser.getFirstName());
        m.addAttribute("lastName", applicationUser.getLastName());
//        m.addAttribute("dateOfBirth", applicationUser.getDate());
        m.addAttribute("bio", applicationUser.getBio());
        List<Post> posts = postRepo.findByUserid (applicationUser);
        m.addAttribute("myposts", posts);
        m.addAttribute("defaultProfilePicture", "https://cdn3.iconfinder.com/data/icons/vector-icons-6/96/256-512.png");

        return "/user-info.html";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException
    {
        ResourceNotFoundException(String message)
        {
            super(message);
        }
    }
}
