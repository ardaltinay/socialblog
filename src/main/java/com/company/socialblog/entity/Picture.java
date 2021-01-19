package com.company.socialblog.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "picture")
public class Picture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Picture() {}

    public Picture(String path, String text, User user) {
        this.path = path;
        this.text = text;
        this.user = user;
        this.active = true;
    }

    public int getId() {
        return id;
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
                ", path='" + path + '\'' +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", active=" + active +
                '}';
    }
}
