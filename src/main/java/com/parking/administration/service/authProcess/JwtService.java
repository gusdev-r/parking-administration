package com.parking.administration.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final GetSignInKeyService getSignInKey;

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("username", String.class);
    }

    public String extractSub(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("sub", String.class);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String detailsUsername = userDetails.getUsername();
        return (extractUsername(token).equals(detailsUsername) || extractSub(token).equals(detailsUsername)
                && !isTokenExpired(token));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey.execute())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
