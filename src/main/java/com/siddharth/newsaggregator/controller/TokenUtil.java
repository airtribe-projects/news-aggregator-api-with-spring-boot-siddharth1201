package com.siddharth.newsaggregator.controller;

import com.siddharth.newsaggregator.entity.User;

import io.jsonwebtoken.Claims;


public class TokenUtil {

    public static String generateToken(User user) {
        String secret = "asdfghjklqwertyuiopzxcvbnm123456789asdfghjklqwertyuiopzxcvbnm123456789";
        return io.jsonwebtoken.Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("role", user.getRole())
                    .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secret)
                    .compact();
    }

    public static Claims getClaims(String token) {
        String secret = "asdfghjklqwertyuiopzxcvbnm123456789asdfghjklqwertyuiopzxcvbnm123456789";
       
        return io.jsonwebtoken.Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        
    }


    public static boolean validateToken(String token) {
        String secret = "asdfghjklqwertyuiopzxcvbnm123456789asdfghjklqwertyuiopzxcvbnm123456789";
        // remove bearer space
       
        Claims body = io.jsonwebtoken.Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        return true;
        
    }
}
