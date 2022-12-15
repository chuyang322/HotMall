package com.example.hotmall.utils;

import java.util.UUID;

public class UUIDUtil {
    public static String getUUIDString(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
