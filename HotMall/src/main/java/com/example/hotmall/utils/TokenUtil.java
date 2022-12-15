package com.example.hotmall.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;
import java.util.UUID;

public class TokenUtil {

    // 过期时间1小时
    private static final long EXPIRE_TIME = 60 * 60 * 1000;

    // 秘钥
    private static final String TOKEN_SECRET = UUID.randomUUID().toString();
    /**
     * 生成token
     *
     * @param id
     * @param password
     * @return
     */
    public static String getToken(int id,String password) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        System.out.println(TOKEN_SECRET);
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

        return JWT.create()
                .withClaim("userId", id)
                .withClaim("userAccount", password)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public static boolean verify(String token) {
        try {
            System.out.println(TOKEN_SECRET);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    public static Integer getUserId(String token){
        return JWT.decode(token).getClaim("userId").asInt();
    }
}
