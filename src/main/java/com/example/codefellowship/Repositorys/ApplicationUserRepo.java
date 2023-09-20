package com.example.codefellowship.Repositorys;

import com.example.codefellowship.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepo extends JpaRepository<ApplicationUser,Long> {


    ApplicationUser findByUsername(String username);
}
