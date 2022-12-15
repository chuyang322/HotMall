package com.example.hotmall.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String MD5Password(String string) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(string.getBytes());
        String result = new BigInteger(1, md.digest()).toString(32);
        return result;
    }
}
