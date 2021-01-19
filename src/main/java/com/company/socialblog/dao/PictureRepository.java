package com.company.socialblog.dao;

import com.company.socialblog.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Integer> {

}
