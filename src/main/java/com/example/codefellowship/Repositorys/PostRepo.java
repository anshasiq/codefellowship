package com.example.codefellowship.Repositorys;

import com.example.codefellowship.model.ApplicationUser;
import com.example.codefellowship.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PostRepo extends JpaRepository<Post,Long> {


//    List<Post> findByUserId(ApplicationUser applicationUser);

    List<Post> findByUserid(ApplicationUser applicationUser);

//    Set<Post> findByUserid(Set<ApplicationUser> app2);


//    List<Post> findAll(ApplicationUser applicationUser);

//    List<Post> findAllById(List<ApplicationUser> app2);

//    Set<Post> findAllById(Set<ApplicationUser> app2);
}
