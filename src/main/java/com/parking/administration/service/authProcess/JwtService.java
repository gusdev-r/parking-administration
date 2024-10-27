package com.parking.administration.service.authProcess;

import com.parking.administration.domain.core.User;
import com.parking.administration.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final GetSignInKeyService getSignInKey;
    private final UserRepository userRepo;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

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

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }
    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshTokenExpiration);
    }

    public String buildToken(Map<String, Object> extraClaims,
                             UserDetails userDetails, long jwtExpiration) {
        User user = (User) userDetails;
        extraClaims.put("username", user.getUsername());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(((User) userDetails).getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey.execute(), SignatureAlgorithm.HS256)
                .compact();
    }
}
