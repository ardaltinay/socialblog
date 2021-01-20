package com.company.socialblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Slf4j
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

    public Picture(String path, String text, User user) {
        log.info("picture has been added.");
        this.path = path;
        this.text = text;
        this.user = user;
        this.active = true;
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
