package com.incense.gehasajang.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
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
                .setExpiration(new Date(new Date().getTime() + JwtProperties.EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

}
