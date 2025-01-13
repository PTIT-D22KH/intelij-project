package com.duongvct.entity;

import com.duongvct.constants.RoleConstant;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private String email;

    @Column()
    private String photo;

    @Column(nullable = false)
    private boolean activated;

    @Enumerated(EnumType.STRING)
    @Column(length = 50) // Ensure the length matches the database schema
    private RoleConstant roles;

//    public Account(String username, String password, String fullname, RoleConstant roles) {
//        this.username = username;
//        this.password = password;
//        this.fullname = fullname;
//        this.roles = roles;
//    }
//
//    public Account(){
//
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public RoleConstant getRoles() {
        return roles;
    }

    public void setRoles(RoleConstant roles) {
        this.roles = roles;
    }
}
