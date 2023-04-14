package com.swerve.backend.shared.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenUtils {
//    @Value("${token.secret}")
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public Claims getClaims(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    public boolean isExpired(String token) {
        try {
            return getClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
        } catch (Exception exception) {
            return true;
        }
    }

    public String getUsername(String token) {
        System.out.println("token:" + token);
        try {
            System.out.println("token2:" + token);
            Claims claims = getClaims(token);
            String subject = claims.getSubject();
            System.out.println("sub:");
            System.out.println("sub:" + subject);
            return subject;

        } catch (Exception exception) {
            System.out.println("exxx");
            return null;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }
}
