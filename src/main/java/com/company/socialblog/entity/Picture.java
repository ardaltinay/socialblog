package com.company.socialblog.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "picture")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    @NotNull
    private int userId;

    @Column(name = "path")
    @NotNull
    private String path;

    @Column(name = "text")
    private String text;

    @Column(name = "timestamp")
    @CreationTimestamp
    private Date timestamp;

    @Column(name = "active")
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(insertable = false, updatable = false, name = "user_id")
    private User user;

    public Picture() {}

    public Picture(int userId, String path, String text, User user) {
        this.userId = userId;
        this.path = path;
        this.text = text;
        this.user = user;
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", userId=" + userId +
                ", path='" + path + '\'' +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", active=" + active +
                '}';
    }
}
