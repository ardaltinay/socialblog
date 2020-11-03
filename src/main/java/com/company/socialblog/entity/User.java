package com.company.socialblog.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @Size(min = 4, max = 20, message = "must be between 4 and 20 characters")
    @NotNull(message = "is required")
    @Pattern(regexp ="[0-9a-z]+", message = "Use only a-z and 0-9 characters")
    private String username;

    @Column(name = "password")
    @Size(min = 8, max = 255, message = "must be greater than 8 characters without whitespace")
    @NotNull(message = "must be greater than 8 characters without whitespace")
    private String password;

    @Column(name = "email")
    @Email(message = "Invalid Email")
    @NotNull(message = "is required")
    private String email;

    @Column(name = "biography")
    private String biography;

    @Column(name = "active")
    private boolean active;

    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = true;
    }

    public int getId() {
        return id;
    }

    /* public void setId(int id) {
        this.id = id;
    } */

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", biography='" + biography + '\'' +
                ", active=" + active +
                '}';
    }
}
