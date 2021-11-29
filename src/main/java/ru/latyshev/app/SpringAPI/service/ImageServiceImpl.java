package ru.latyshev.app.SpringAPI.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static org.reflections.Reflections.log;


@Service
public class ImageServiceImpl implements ImageService{

    private String uploadPath;


    @Override
    public String uploadImage(MultipartFile file) throws IOException {

        if (file!=null){
            log.info("Creating upload");

            String fileName= UUID.randomUUID()+file.getOriginalFilename();
            File uploadDir=new File(uploadPath+fileName);
            uploadDir.getParentFile().mkdirs();

            log.warn(uploadDir.getAbsolutePath());

            if(uploadDir.createNewFile()){
                log.info("File path created");
                FileOutputStream fileOutputStream=new FileOutputStream(uploadDir);
                fileOutputStream.write(file.getBytes());
                fileOutputStream.close();
            } else {
                throw new IOException("File not created");
            }
            return fileName;
        } else {
            throw new IOException("File is null");
        }

    }

    @Override
    public byte[] getImageByName(String name) throws IOException {
        File imageFile=new File(uploadPath+name);
        FileInputStream fileInputStream= new FileInputStream(imageFile);
        byte[] data=new byte[(int) imageFile.length()];
        return fileInputStream.readAllBytes();


    }
}
