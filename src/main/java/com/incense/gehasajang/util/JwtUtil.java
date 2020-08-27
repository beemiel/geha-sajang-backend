package com.incense.gehasajang.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtUtil {

    private SecretKey key;

    public JwtUtil() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }


    public Claims parseToken(String token) {
                    return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    public String createToken(String account, String role) {
        return Jwts.builder()
                .claim("account", account)
                .claim("role", role)
                .signWith(key)
                .compact();
    }

}
