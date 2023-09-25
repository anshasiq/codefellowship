package com.example.codefellowship.Repositorys;

import com.example.codefellowship.model.ApplicationUser;
import com.example.codefellowship.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {


//    List<Post> findByUserId(ApplicationUser applicationUser);

    List<Post> findByUserid(ApplicationUser applicationUser);
}
