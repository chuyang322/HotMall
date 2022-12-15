package com.example.hotmall.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    public static Map<String, Object> objectToMap(Object o) {
        Map<String, Object> map = new HashMap<>();
        if (o == null) return map;
        Class clazz=o.getClass();
        Field[] fields=clazz.getDeclaredFields();
        for (Field f:fields) {
            f.setAccessible(true);
            try {
                map.put(f.getName(),f.get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
        return map;
    }
}
