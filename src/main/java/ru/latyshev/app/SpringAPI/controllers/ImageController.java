package ru.latyshev.app.SpringAPI.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.latyshev.app.SpringAPI.model.ModelDataWrapper;
import ru.latyshev.app.SpringAPI.service.ImageService;

import java.io.IOException;

import static org.reflections.Reflections.log;

@RestController
@RequestMapping("/v1/public/images")
@Api("Marvel image controller")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/get/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation(value = "This will get image file by name")
    public byte[] getImage(@PathVariable String name) throws IOException {
        return imageService.getImageByName(name);

    }
    @RequestMapping(method = RequestMethod.POST, value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "This will upload image file on server and return model with generated new file name.")
    public ModelDataWrapper<Object> uploadImage(@RequestParam ("file") MultipartFile file ){
        log.warn("check");
        ModelDataWrapper<Object> responseModel=new ModelDataWrapper<>();
        try{
            String fileName=imageService.uploadImage(file);
            responseModel.setCode(200).setStatus("File save with name:"+fileName);
        } catch (IOException e) {
            return responseModel.setCode(500).setStatus("Image non upload"+e.getMessage());
        }
        return responseModel;
    }
}
