package com.example.codefellowship.model;


import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class Post {

//    private Object GenerationType;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ApplicationUser userId;

    private String body;

    private LocalDate createdAt;
    
    
    
}
