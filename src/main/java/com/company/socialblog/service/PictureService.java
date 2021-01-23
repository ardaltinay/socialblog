package com.company.socialblog.service;

import com.company.socialblog.entity.Picture;

import java.util.List;

public interface PictureService {

    void savePicture(Picture picture);

    Picture findPictureById(int id);

    List<Picture> getPictures();
}
