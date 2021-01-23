package com.company.socialblog.service;

import com.company.socialblog.dao.PictureRepository;
import com.company.socialblog.entity.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public void savePicture(Picture picture) {
        pictureRepository.save(picture);
    }

    @Override
    public Picture findPictureById(int id) {
        return pictureRepository.getOne(id);
    }

    @Override
    public List<Picture> getPictures() {
        return pictureRepository.findAll();
    }
}
