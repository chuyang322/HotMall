package com.example.hotmall.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static String upload(String path, MultipartFile file){
        String newFileName;
        try {
            //新文件名
            newFileName=getNewFileName(file.getOriginalFilename());
        }catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }

        String newPath=path+newFileName;
        File f=new File(newPath);
        System.out.println(newPath);
        //目录不存在创建目录
        if(!f.getParentFile().exists()){
            f.getParentFile().mkdir();
        }

        try {
            file.transferTo(f);
            return newPath;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件后缀
     * @param fileName 文件名
     * @return 文件后缀
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 通过uuid获取新的随机文件名
     * @param fileName 旧文件名
     * @return 新文件名
     */
    public static String getNewFileName(String fileName){
        return UUIDUtil.getUUIDString()+getSuffix(fileName);
    }

}
