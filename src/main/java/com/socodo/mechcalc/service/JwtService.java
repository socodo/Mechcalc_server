package com.socodo.mechcalc.service;

import com.socodo.mechcalc.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long accessTokenExpirationMillis;

    public JwtService(
            @Value("${security.jwt.secret-key}") String secretKey,
            @Value("${security.jwt.expiration-time}") long accessTokenExpirationMillis
    ) {
        this.signingKey = buildSigningKey(secretKey);
        this.accessTokenExpirationMillis = accessTokenExpirationMillis;
    }

    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpirationMillis, "access");
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, accessTokenExpirationMillis * 24, "refresh");
    }

    public long getAccessTokenExpirationSeconds() {
        return accessTokenExpirationMillis / 1000;
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    public boolean isAccessTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Object type = claims.get("type");
            return "access".equals(type);
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    private String generateToken(User user, long expirationMillis, String type) {
        Instant now = Instant.now();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId() != null ? user.getId().toString() : null);
        claims.put("email", user.getEmail());
        claims.put("type", type);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(expirationMillis)))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey buildSigningKey(String secretKey) {
        try {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        } catch (IllegalArgumentException exception) {
            return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
