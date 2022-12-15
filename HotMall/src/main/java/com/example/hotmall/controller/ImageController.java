package com.example.hotmall.controller;

import com.example.hotmall.utils.APIResult;
import com.example.hotmall.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/images")
public class ImageController {
    @Value("${web.upload-path}")
    private String path;

    @RequestMapping("/upload")
    public APIResult uploadImage(@RequestParam("image") MultipartFile multipartFile){
        String destinationPath=path;
        String newFileName= FileUtil.upload(destinationPath,multipartFile);
        try{
            newFileName=newFileName.replace("C:","http://localhost:8080");
            return APIResult.createOk(newFileName);
        }catch (Exception e){
            e.printStackTrace();
            return APIResult.createNg("图片上传失败");
        }
    }
}
