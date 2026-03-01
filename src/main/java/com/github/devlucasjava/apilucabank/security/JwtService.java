package com.github.devlucasjava.apilucabank.security;

import com.github.devlucasjava.apilucabank.exception.CustomSignatureException;
import com.github.devlucasjava.apilucabank.exception.TokenExpiredException;
import com.github.devlucasjava.apilucabank.model.Users;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtService {

    public Long jwtExpiration;
    private final Key key;

    // Constructor with variable of systems
    public JwtService(@Value("${jwt.secret}") String jwtSecret,
                      @Value("${jwt.expiration}") Long jwtExpiration
    ) {
        this.jwtExpiration = jwtExpiration;
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Users users) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(users.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(jwtExpiration)))
                .addClaims(Map.of(
                        "roles", users.getAuthorities().stream()
                                .map(auth -> auth.getAuthority())
                                .toList()
                ))
                .signWith(key, SignatureAlgorithm.HS384) // balance between safety and performance
                .compact();
    }

    public Claims parseChains(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Token Expired");
        } catch (JwtException e) {
            throw new CustomSignatureException("Token Invalid");
        }
    }

    public String extractUsername(String token) {
        return parseChains(token).getSubject();
    }
    public boolean isTokenValid(String token, Users users) {
        final String username = extractUsername(token);
        return username.equals(users.getUsername()) && !isTokenExpired(token);
    }
    public boolean isTokenExpired(String token) {
        return parseChains(token).getExpiration().before(Date.from(Instant.now()));
    }
}
