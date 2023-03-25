package com.example.meusgastos.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.meusgastos.domain.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${auth.jwt.secret}")
    private String jwtSecret;

    @Value("${auth.jwt.expiration}")
    private Long jwtExpiration;

    public String generateToken(Authentication auth) {

        Date expirationDate = new Date(new Date().getTime() + jwtExpiration);

        User user = (User) auth.getPrincipal();

        try {

            Key jwtsKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));

            return Jwts.builder()
                    .setSubject(user.getEmail())
                    .setIssuedAt(new Date())
                    .setExpiration(expirationDate)
                    .signWith(jwtsKey)
                    .compact();

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return "";
        }
    }

    private Claims getClaims(String token) {

        try {

            Key jwtsKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));

            Claims claims = Jwts.parserBuilder().setSigningKey(jwtsKey).build().parseClaimsJws(token).getBody();

            return claims;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getEmail(String token) {

        Claims claims = getClaims(token);

        if (claims == null) {
            return null;
        }

        return claims.getSubject();
    }

    public boolean checkIfTokenIsValid(String token) {

        Claims claims = getClaims(token);

        if (claims == null) {
            return false;
        }

        String email = claims.getSubject();
        Date expirationDate = claims.getExpiration();

        Date now = new Date(System.currentTimeMillis());

        if (email != null && now.before(expirationDate)) {
            return true;
        } else {
            return false;
        }
    }
}