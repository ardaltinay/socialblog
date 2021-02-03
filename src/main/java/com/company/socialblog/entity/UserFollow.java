package com.company.socialblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id_to")
    private int userIdTo;
    @Column(name = "user_id_from")
    private int userIdFrom;
    @Column(name = "timestamp")
    @CreationTimestamp
    private Date timestamp;
}
