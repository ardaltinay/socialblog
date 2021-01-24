package com.company.socialblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Slf4j
@Table(name = "user")
public class User implements Serializable {

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

    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @OneToMany(mappedBy = "user")
    private List<Picture> pictures;

    @ManyToMany(mappedBy = "likedUsers")
    private List<Picture> likedPictures;

    public User(String username, String password, String email) {
        log.info("User has been added.");
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = true;
    }

    public void addLikedPicture(Picture p) {
        likedPictures.add(p);
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
                ", timestamp=" + timestamp +
                '}';
    }
}
