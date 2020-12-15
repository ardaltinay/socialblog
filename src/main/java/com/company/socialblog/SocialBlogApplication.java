package com.company.socialblog;

import com.company.socialblog.entity.Picture;
import com.company.socialblog.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication()
public class SocialBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialBlogApplication.class, args);
    }

}
