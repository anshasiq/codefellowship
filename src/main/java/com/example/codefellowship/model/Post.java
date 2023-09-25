package com.example.codefellowship.model;


import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    private LocalDate createdAt;
    @ManyToOne
    private ApplicationUser userid;

    public Post() {
    }

    public Post(String body, LocalDate createdAt, ApplicationUser userid) {
        this.body = body;
        this.createdAt = createdAt;
        this.userid = userid;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public ApplicationUser getUserid() {
        return userid;
    }

    public void setUserid(ApplicationUser userid) {
        this.userid = userid;
    }
}
