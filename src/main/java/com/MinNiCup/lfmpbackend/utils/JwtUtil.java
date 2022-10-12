package com.MinNiCup.lfmpbackend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Calendar;
import java.util.Map;

/**
 * @author tzih
 * &#064;date  2022.10.09
 */
public class JwtUtil {

    // 签名密钥
    private static final String SECRET = "!DAR$";

    /**
     * 生成token
     * @param payload token携带的信息
     * @return token字符串
     */
    public static String getToken(Map<String,String> payload) {
        // 指定token过期时间为15天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);

        JWTCreator.Builder builder = JWT.create();
        // 构建payload
        payload.forEach(builder::withClaim);
        // 指定过期时间和签名算法
        return builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(SECRET));

    }

    /**
     * 解析token
     * @param token token字符串
     * @return 解析后的token
     */
    public static DecodedJWT decode(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = jwtVerifier.verify(token);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return decodedJWT;
    }

}

