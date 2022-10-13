package com.github.damian_git_99.backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JWTServiceImpl implements JWTService {

    private final Date TWO_HOURS = new Date(System.currentTimeMillis() + (10 * 6000 * 120));

    public static final Key SECRET_KEY =
            Keys.hmacShaKeyFor("MY_SUPER_PRIVATE_KEY_QWERTY_54321".getBytes(StandardCharsets.UTF_8));

    @Override
    public String createToken(String subject, Map<String, Object> payload) {
        Claims claims = Jwts.claims();
        claims.putAll(payload);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(TWO_HOURS)
                .signWith(SECRET_KEY)
                .compact();
    }

    @Override
    public boolean validateToken(String header) {
        return false;
    }

    @Override
    public Claims getClaims(String authorizationHeader) {
        return null;
    }
}
