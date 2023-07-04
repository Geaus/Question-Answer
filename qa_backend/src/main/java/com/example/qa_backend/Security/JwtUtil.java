package com.example.qa_backend.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static String key = "illyaks";
    private static int expire_time = 1800000;
    public static String  generalKey(){
        // 加密
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(key.getBytes());

        // 解密
/*
		try {
            String s1 = new String(Base64.getDecoder().decode(加密后的字符串.getBytes()), "UTF-8");
            System.out.println(s1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(int id) {
        Date exp = new Date(System.currentTimeMillis() + expire_time);
        Map<String, Object> claims = new HashMap<>(1);
        claims.put("id", id);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, generalKey())
                .compact();

        return token;
    }

    /**
     * 校验token并解析token
     */
    public static int getUserId(String token) {

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(generalKey())
                    .parseClaimsJws(token)
                    .getBody();
            int id = Integer.parseInt(claims.get("id").toString());
            return  id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
