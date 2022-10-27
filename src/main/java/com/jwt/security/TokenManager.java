package com.jwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenManager implements Serializable {
    private static final long serialVersionUID = 7008375124389347049L;

    public static final long TOKEN_VALIDITY = 10 * 60 * 60;

    @Value("${secret}")
    private String secretKey;

    public String generateJwt(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TokenManager.TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Boolean validateJwt(String token, UserDetails userDetails){

        String username = getUsernameFromToken(token);

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Boolean isTokenExpired = claims.getExpiration()
                .before(new Date());

        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }

    public String getUsernameFromToken(String token){
        final Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();

    }





}
