package com.company.socialblog.service;

import com.company.socialblog.dao.PictureRepository;
import com.company.socialblog.entity.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    @Transactional
    public void savePicture(Picture picture) {
        pictureRepository.save(picture);
    }

    @Override
    @Transactional
    public Picture findPictureById(int id) {
        return pictureRepository.getOne(id);
    }

    @Override
    @Transactional
    public List<Picture> getPictures() {
        return pictureRepository.findAll();
    }
}
