package ru.latyshev.app.SpringAPI.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String uploadImage(MultipartFile file) throws IOException;

    byte[] getImageByName(String name) throws IOException;
}
